package ui.panels;

import java.lang.*;
import entity.*;
import storage.*;

public class PostManagementListPanel extends ManagementListPanel{
    public PostManagementListPanel(){
        super();
        this.filterRows("");
    }

    protected void addMatchingRows(String search){
        for(Post post: PostStorage.getPosts().values()){
            String postId = String.valueOf(post.getPostId());
            String author = post.getAuthor().toLowerCase();
            String content = post.getContent().toLowerCase();

            boolean postIdMatches = postId.contains(search);
            boolean authorMatches = author.contains(search);
            boolean contentMatches = content.contains(search);
            boolean postMatches = postIdMatches || authorMatches || contentMatches;

            if(postMatches){
                this.add(new PostManagementRowPanel(post));
            }
        }
    }
}
