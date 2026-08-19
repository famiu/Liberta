package frames;

import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import storage.*;
import entity.UserAccount;

public class Register extends JFrame implements ActionListener{
    private ImageIcon icon;
    private JTextField userfld, namefld,emailfld;
    private JPasswordField passfld;
    private JButton loginbtn, registerbtn;
    private JFormattedTextField datefld;
    public Register() {
        super("Liberta: Embrace your freedom");
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,2));

        JPanel brand = new JPanel();
        JPanel register = new JPanel();

        brand.setLayout(new GridBagLayout());
        JLabel logo = new JLabel(new ImageIcon("./assets/branding/svg/png/512/logo-vertical.png"));
        logo.setSize(400,400);
        brand.add(logo);

        register.setLayout(new BoxLayout(register,BoxLayout.Y_AXIS));
        register.setBackground(Color.WHITE);

        
        JLabel username = new JLabel("Username"), pass = new JLabel("Password"), name = new JLabel("Display Name");
        JLabel date = new JLabel("Date of birth(yyyy-MM-dd)"), email = new JLabel("Email");
        name.setMaximumSize(new Dimension(300,50));
        username.setMaximumSize(new Dimension(300,50));
        pass.setMaximumSize(new Dimension(300,50));
        email.setMaximumSize(new Dimension(300,50));
        date.setMaximumSize(new Dimension(300,50));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);
        date.setAlignmentX(Component.CENTER_ALIGNMENT);
        email.setAlignmentX(Component.CENTER_ALIGNMENT);

        namefld = new JTextField(20);
        namefld.setMaximumSize(new Dimension(300,50));
        namefld.setAlignmentX(Component.CENTER_ALIGNMENT);
        userfld = new JTextField(20);
        userfld.setMaximumSize(new Dimension(300,50));
        userfld.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailfld = new JTextField(20);
        emailfld.setMaximumSize(new Dimension(300,50));
        emailfld.setAlignmentX(Component.CENTER_ALIGNMENT);
        passfld = new JPasswordField(20);
        passfld.setMaximumSize(new Dimension(300,50));
        passfld.setPreferredSize(new Dimension(300,50));
        passfld.setFont(new Font("Arial", Font.PLAIN, 25));
        passfld.setAlignmentX(Component.CENTER_ALIGNMENT);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        datefld = new JFormattedTextField(dateFormat);
        datefld.setColumns(10);
        datefld.setMaximumSize(new Dimension(300,50));
        datefld.setPreferredSize(new Dimension(300,50));
        datefld.setFont(new Font("Arial", Font.PLAIN, 25));
        datefld.setValue(new Date());

        registerbtn = new JButton("Register");
        loginbtn = new JButton("Already have an account? Login here");
        loginbtn.setContentAreaFilled(false);
        loginbtn.setBorderPainted(false);
        registerbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerbtn.addActionListener(this);
        loginbtn.addActionListener(this);

        register.add(Box.createVerticalGlue());
        register.add(name);
        register.add(namefld);
        register.add(Box.createRigidArea(new Dimension(0,15)));
        register.add(username);
        register.add(userfld);
        register.add(Box.createRigidArea(new Dimension(0,15)));
        register.add(email);
        register.add(emailfld);
        register.add(Box.createRigidArea(new Dimension(0,15)));
        register.add(date);
        register.add(datefld);
        register.add(Box.createRigidArea(new Dimension(0,15)));
        register.add(pass);
        register.add(passfld);
        register.add(Box.createRigidArea(new Dimension(0,25)));
        register.add(registerbtn);
        register.add(Box.createRigidArea(new Dimension(0,15)));
        register.add(loginbtn);
        register.add(Box.createVerticalGlue());

        this.add(brand);
        this.add(register);

        icon = new ImageIcon("./assets/branding/svg/png/64/icon.png");
        this.setIconImage(icon.getImage());

        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==registerbtn){
            String username = userfld.getText(), name = namefld.getText(), email = emailfld.getText();
            String password = new String(passfld.getPassword());
            LocalDate dateOfBirth = LocalDate.parse(datefld.getText());
            if(UserStorage.checkUser(username)){
                showMessageDialog(null, "This user already exist","Warning",JOptionPane.INFORMATION_MESSAGE);
            }else{
                UserStorage.addUser(new UserAccount(username,password,name,email,"",dateOfBirth));
            }
        }
    }
}
