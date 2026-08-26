package frames;

import javax.swing.*;

import storage.*;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener{
    private ImageIcon icon;
    private JTextField userfld;
    private JPasswordField passfld;
    private JButton loginbtn, registerbtn;
    private JRadioButton user, admin;
    private Register regPage;
    public Login() {
        super("Liberta: Embrace your freedom");
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2));

        JPanel brand = new JPanel();
        JPanel login = new JPanel();

        brand.setLayout(new GridBagLayout());
        JLabel logo = new JLabel(new ImageIcon("./assets/branding/png/512/logo-vertical.png"));
        logo.setSize(400,400);
        brand.add(logo);

        login.setLayout(new GridLayout(2,1));
        login.setBackground(Color.WHITE);

        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input,BoxLayout.Y_AXIS));
        input.setBackground(Color.WHITE);

        user = new JRadioButton("User");
        admin = new JRadioButton("Admin");
        JPanel identity = new JPanel();
        ButtonGroup group = new ButtonGroup();
        identity.setBackground(Color.WHITE);
        identity.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
        user.setBackground(Color.WHITE);
        admin.setBackground(Color.WHITE);
        group.add(user);
        group.add(admin);
        identity.add(user);
        identity.add(admin);
        user.setSelected(true);
        JLabel username = new JLabel("Username"), pass = new JLabel("Password");
        username.setMaximumSize(new Dimension(300,50));
        pass.setMaximumSize(new Dimension(300,50));
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);

        userfld = new JTextField(20);
        userfld.setMaximumSize(new Dimension(300,50));
        userfld.setAlignmentX(Component.CENTER_ALIGNMENT);
        passfld = new JPasswordField(20);
        passfld.setMaximumSize(new Dimension(300,50));
        passfld.setPreferredSize(new Dimension(300,50));
        passfld.setFont(new Font("Arial", Font.PLAIN, 25));
        passfld.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        input.add(Box.createVerticalGlue());
        input.add(identity);
        input.add(username);
        input.add(userfld);
        input.add(Box.createRigidArea(new Dimension(0,15)));
        input.add(pass);
        input.add(passfld);

        JPanel btn = new JPanel();
        btn.setLayout(new BoxLayout(btn,BoxLayout.Y_AXIS));
        btn.setBackground(Color.WHITE);

        loginbtn = new JButton("Login");
        registerbtn = new JButton("Don't have an account? Register here");
        registerbtn.setContentAreaFilled(false);
        registerbtn.setBorderPainted(false);
        loginbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginbtn.addActionListener(this);
        registerbtn.addActionListener(this);
        
        btn.add(Box.createRigidArea(new Dimension(0,25)));
        btn.add(loginbtn);
        btn.add(Box.createRigidArea(new Dimension(0,15)));
        btn.add(registerbtn);

        login.add(input);
        login.add(btn);

        this.add(brand);
        this.add(login);

        icon = new ImageIcon("./assets/branding/png/64/icon.png");
        this.setIconImage(icon.getImage());

        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==loginbtn){
            String username = userfld.getText(), password = new String(passfld.getPassword());
            if(username.isEmpty()||password.isEmpty()){
                showMessageDialog(null, "Please fill up all the fields","Warning", JOptionPane.WARNING_MESSAGE);
            }else if(user.isSelected()){
                if(!UserStorage.checkUser(username)){
                    showMessageDialog(null, "This user does not exist","Warning", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if(!UserStorage.checkPassword(username, password)){
                        passfld.setText("");
                        showMessageDialog(null,"Password does not match","Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }else if(admin.isSelected()){
                if(!AdminStorage.checkAdmin(username)){
                    showMessageDialog(null, "This user does not exist","Warning", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if(!AdminStorage.checkPassword(username, password)){
                        passfld.setText("");
                        showMessageDialog(null,"Password does not match","Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        else if(e.getSource()==registerbtn){
            this.setVisible(false);
            
            if(regPage==null){
                regPage = new Register(this);
            }
            regPage.setVisible(true);
        }
    }
}
