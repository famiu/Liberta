package storage;

import java.lang.*;
import java.io.*;
import java.util.*;
import java.time.*;

import entity.*;

// Class to handle storage of Post objects.
//
// Post metadata is stored in data/posts.txt, with one post per line and semicolon-separated
// fields: postId;author;timestamp. Post content is stored in data/posts/<postId>.txt.
//
// Posts are loaded into a map in memory for fast access, and changes are written back to the data
// files after any modification.
public class PostStorage {
    private static final File postsFile = new File("data/posts.txt");
    private static final File postsDirectory = new File("data/posts");
    private static HashMap<Integer, Post> posts = new HashMap<>();

    // Ensure that the database paths exist and populate the posts map by reading the database
    static {
        StorageUtility.ensurePath(postsFile.getParent(), postsFile.getName());
        StorageUtility.ensurePath(postsDirectory.getPath());
        readPostsFromFile();
    }

    // Don't need constructor since class is fully static, so make it private to prevent
    // instantiation
    private PostStorage() {
    }

    public static HashMap<Integer, Post> getPosts() {
        return posts;
    }

    public static Post getPost(int postId) {
        return posts.get(postId);
    }

    public static boolean postExists(int postId) {
        return posts.containsKey(postId);
    }

    public static void addPost(Post newPost) {
        if (posts.containsKey(newPost.getPostId())) {
            System.out.println("Post with ID " + newPost.getPostId() + " already exists");
        } else if (!UserStorage.checkUser(newPost.getAuthor())) {
            System.out.println("User is not found");
        } else {
            posts.put(newPost.getPostId(), newPost);
            UserStorage.addPost(newPost.getAuthor(), newPost.getPostId());

            try (FileWriter metadataWriter = new FileWriter(postsFile, true)) {
                metadataWriter.write(newPost.getPostId() + ";" + newPost.getAuthor() + ";" + newPost.getTimestamp() + "\n");
            } catch (IOException e) {
                System.out.println("Error writing post metadata to file");
            }

            writePostContent(newPost);
        }
    }

    public static void updatePost(int postId, String content) {
        if (posts.containsKey(postId)) {
            Post post = posts.get(postId);
            post.setContent(content);
            writePostContent(post);
        } else {
            System.out.println("Post with ID " + postId + " does not exist");
        }
    }

    public static void deletePost(int postId) {
        if (posts.containsKey(postId)) {
            String username = posts.get(postId).getAuthor();
            deletePostNoWrite(postId);
            UserStorage.deletePost(username, postId);
            updatePostsMetadata();
        } else {
            System.out.println("Post with ID " + postId + " does not exist");
        }
    }

    public static void deletePostsByAuthor(String username) {
        ArrayList<Integer> postsToDelete = new ArrayList<>();
        for (Post post : posts.values()) {
            if (post.getAuthor().equals(username)) {
                postsToDelete.add(post.getPostId());
            }
        }

        for (Integer postId : postsToDelete) {
            deletePostNoWrite(postId);
        }

        if (!postsToDelete.isEmpty()) {
            UserStorage.deletePosts(username);
            updatePostsMetadata();
        }
    }

    private static File getPostFile(int postId) {
        return new File(postsDirectory, postId + ".txt");
    }

    // Read the content of a post from its corresponding content file.
    private static String getPostContent(int postId) {
        File postFile = getPostFile(postId);
        if (!postFile.exists()) {
            System.out.println("Post file for postId " + postId + " does not exist");
            return null;
        }
        StringBuilder content = new StringBuilder();
        try (Scanner contentScanner = new Scanner(postFile)) {
            while (contentScanner.hasNextLine()) {
                content.append(contentScanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading post content from file");
            return null;
        }
        return content.toString().trim();
    }

    // Read posts from the metadata file and load them into the posts map.
    private static void readPostsFromFile() {
        posts.clear();

        try (Scanner metadataScanner = new Scanner(postsFile)) {
            while (metadataScanner.hasNextLine()) {
                String postInfoString = metadataScanner.nextLine();
                String postInfo[] = postInfoString.split(";");

                Integer postId = Integer.parseInt(postInfo[0]);
                String author = postInfo[1];
                LocalDateTime timestamp = LocalDateTime.parse(postInfo[2]);
                String content = getPostContent(postId);

                if (content == null) {
                    System.out.println("Post content for post ID " + postId + " not found");
                    continue;
                }

                Post post = new Post(postId, author, content, timestamp);
                posts.put(postId, post);
            }
        }
        catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    private static void writePostContent(Post post) {
        File postFile = getPostFile(post.getPostId());
        try (FileWriter contentWriter = new FileWriter(postFile)) {
            contentWriter.write(post.getContent());
        } catch (IOException e) {
            System.out.println("Error writing post content to file");
        }
    }

    private static void updatePostsMetadata() {
        try (FileWriter metadataWriter = new FileWriter(postsFile)) {
            for (Post post : posts.values()) {
                metadataWriter.write(post.getPostId() + ";" + post.getAuthor() + ";" + post.getTimestamp() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error rewriting posts file");
        }
    }

    // Utility function to delete a post without writing to file, useful for deleting multiple
    // posts at once without writing to file each time
    private static void deletePostNoWrite(int postId) {
        if (posts.containsKey(postId)) {
            File postFile = getPostFile(postId);
            if (postFile.exists()) {
                postFile.delete();
            }
            posts.remove(postId);
        } else {
            System.out.println("Post with ID " + postId + " does not exist");
        }
    }
}
