/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyMenu;

/**
 *
 * @author bradisa
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.lang.String;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.event.*;
import  javax.swing.text.Element;
import javax.swing.JOptionPane;

class MyMenuesBA extends Frame implements ActionListener
{
    private Menu fileBA,EditBA,formatBA,HelpBA;
    private MenuBar mb=new MenuBar();
    private MenuItem NewBA,OpenBA,SaveBA,exitBA,findBA,find_nextBA,replaceBA,gotoBA_,greenBA,redBA,about_notpadBA;


    private JTextArea txta_show;
    private JTextArea lines;
    private JScrollPane jsp;

    public MyMenuesBA()
    {

        setTitle("Bolanle's GUI");
        setSize(500,500);
        setLocation(100,100);

        /*----------------file----------------*/
        NewBA=new MenuItem("New");
        OpenBA=new MenuItem("Open");
        SaveBA=new MenuItem("Save");
        exitBA=new MenuItem("Exit");

        fileBA=new Menu("File");

        fileBA.add(NewBA);
        fileBA.add(OpenBA);
        fileBA.add(SaveBA);
        fileBA.addSeparator();
        fileBA.add(exitBA);
        //fileBA.add(input);

        mb.add(fileBA);

        /*-------------Edit--------------*/
        findBA=new MenuItem("Find");
        find_nextBA=new MenuItem("Find Next");
        replaceBA=new MenuItem("Replace");
        gotoBA_=new MenuItem("Goto..");

        EditBA=new Menu("Edit");

        EditBA.add(findBA);
        EditBA.add(replaceBA);
        EditBA.add(gotoBA_);

        mb.add(EditBA);

        /*--------------format------------------*/
        greenBA=new MenuItem("Green");
        redBA=new MenuItem("Red");

        formatBA=new Menu("Format");
        formatBA.add(greenBA);
        formatBA.add(redBA);

        mb.add(formatBA);

        /*-----------------help-----------------*/
        about_notpadBA=new MenuItem("About Bolanles's GUI");

        HelpBA=new Menu("Help");

        HelpBA.add(about_notpadBA);

        mb.add(HelpBA);
        /*-------------------textarea--------------------*/
        txta_show=new JTextArea();
        add(txta_show);

        setMenuBar(mb);
        /*-------------------------------------*/

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);

            }
        });
        /*---------------addactionlistener------------------*/
        NewBA.addActionListener(this);
        OpenBA.addActionListener(this);
        SaveBA.addActionListener(this);
        exitBA.addActionListener(this);
        findBA.addActionListener(this);
        find_nextBA.addActionListener(this);
        replaceBA.addActionListener(this);
        gotoBA_.addActionListener(this);
        greenBA.addActionListener(this);
        redBA.addActionListener(this);
        about_notpadBA.addActionListener(this);

        /*----------------------scroll------------------*/
        jsp = new JScrollPane();
        txta_show = new JTextArea();
        lines = new JTextArea("1");
        lines.setEditable(false);
        txta_show.getDocument().addDocumentListener(new DocumentListener() {
            public String getText(){
                int position = txta_show.getDocument().getLength();
                Element root = txta_show.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for(int i =2; i < root.getElementIndex(position) +2; i++){
                    text += i + System.getProperty("line.separator");

                }
                for (int i = 0; i < root.getElementIndex(position); i++){
                    if((i) % 2 ==0){
                        lines.setBackground(Color.cyan);
                    }else{
                        lines.setBackground(Color.white);
                    }
                }
                return text;
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                lines.setText(getText());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                lines.setText(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lines.setText(getText());
            }
        });
        jsp.getViewport().add(txta_show);
        jsp.setRowHeaderView(lines);
        add(jsp);
        setVisible(true);
    }

    void goTOBA(){
        int line = 0;
        try{
            line = txta_show.getLineOfOffset(txta_show.getCaretPosition()) + 1;
            String test = JOptionPane.showInputDialog("Go To line Number", " " + line);
            if (test == null){
                return;
            }
            line = Integer.parseInt(test);
            txta_show.setCaretPosition(txta_show.getLineStartOffset(line -1 ));

        }catch(Exception e){

        }
    }


    public void actionPerformed(ActionEvent ae)
    {
        /*---------------file menu----------------------*/
        if(ae.getSource()==NewBA)
        {
            txta_show.setText(" ");
        }
        else if(ae.getSource()==OpenBA)
        {
            try
            {
                FileDialog fd=new FileDialog(this,"Open File",FileDialog.LOAD);
                fd.setVisible(true);
                String dir=fd.getDirectory();
                String fname=fd.getFile();
                FileInputStream fis=new FileInputStream(dir+fname);
                DataInputStream dis=new DataInputStream(fis);
                String str=" ",msg=" ";
                while((str=dis.readLine())!=null)
                {
                    msg=msg+str;
                    msg+="\n";
                }
                txta_show.setText(msg);
                dis.close();
            }
            catch(Exception e){}
        }
        ////////////*Go to Menu *///////////////////
        else if (ae.getSource()== gotoBA_){
            goTOBA();
        }
        else if(ae.getSource()==SaveBA)
        {
            try
            {
                FileDialog fd=new FileDialog(this,"Save File",FileDialog.SAVE);
                fd.setVisible(true);
                String txt=txta_show.getText();
                String dir=fd.getDirectory();
                String fname=fd.getFile();
                FileOutputStream fos=new FileOutputStream(dir+fname);
                DataOutputStream dos=new DataOutputStream(fos);
                dos.writeBytes(txt);
                dos.close();
            }
            catch(Exception e)
            {

            }
        }

        else if(ae.getSource()==exitBA)
        {
            new Exit_Window().setVisible(true);
        }

        else if (ae.getSource() == greenBA)
        {
            txta_show.setForeground(Color.green);
        }
        else if (ae.getSource() == redBA)
        {
            txta_show.setForeground(Color.red);
        }

        /*-----------------------Help Menu------------------------------*/
        else if(ae.getSource()==about_notpadBA)
        {
            new MyDialog().setVisible(true);
        }
    }
}
class Exit_Window extends Dialog implements ActionListener
{
    private Label lbl_show_msg;
    private Button btn_yes,btn_no;

    public Exit_Window()
    {
        super(new MyMenuesBA(),"Exit",true);
        setLayout(new FlowLayout());

        lbl_show_msg=new Label("Do you want to exit this window?");
        add(lbl_show_msg);

        btn_yes=new Button("Yes");
        btn_yes.addActionListener(this);
        add(btn_yes);

        btn_no=new Button("No");
        btn_no.addActionListener(this);
        add(btn_no);

        setTitle("Exit Window");
        setSize(250,200);
        setLocation(200,200);
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==btn_yes)
        {
            System.exit(0);
        }
        else
            dispose();
    }
}

class FindReplace extends JFrame implements ActionListener
{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    JTextPane jTextPane;
    private int findPosn = 0;
    /** the last text searched for */
    private String findText = null;
    /** case sensitive find/replace */
    private boolean findCase = false;


    public FindReplace(JTextPane jt)
    {
        setResizable(false);
        setTitle("Find Replace");

        jTextPane=jt;
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 440, 215);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton Find = new JButton("Find");
        Find.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        Find.setBounds(312, 32, 89, 23);
        contentPane.add(Find);

        JButton Find_Cancel = new JButton("Cancel");
        Find_Cancel.setBounds(312, 80, 89, 23);
        contentPane.add(Find_Cancel);

        JLabel lblNewLabel = new JLabel("Find What");
        lblNewLabel.setBounds(10, 54, 75, 23);
        contentPane.add(lblNewLabel);

    }

    public void doFindText(String find) {
        int nextPosn = 0;
        if (!find.equals(findText) ) // *** new find word
            findPosn = 0; // *** start from top
        nextPosn = nextIndex( jTextPane.getText(), find, findPosn, findCase );

        if ( nextPosn >= 0 ) {
            int l=getLineNumber(jTextPane,nextPosn+find.length());
            System.out.print(l);
            jTextPane.setSelectionStart( nextPosn-l); // position cursor at word start
            jTextPane.setSelectionEnd( nextPosn+ find.length()-l+1);
            findPosn = nextPosn + find.length()+1; // reset for next search
            findText = find; // save word & case
        } else {
            findPosn = nextPosn; // set to -1 if not found
            JOptionPane.showMessageDialog(null, find + " not Found!" );
        }
    }
    public void doReplaceWords(String find, String replace) {
        int nextPosn = 0;
        StringBuffer str = new StringBuffer();
        findPosn = 0; // *** begin at start of text
        while (nextPosn >= 0) {
            nextPosn = nextIndex( jTextPane.getText(), find, findPosn, findCase );

            if ( nextPosn >= 0 ) { // if text is found
                int rtn = JOptionPane.YES_OPTION; // default YES for confirm
                jTextPane.grabFocus();
                jTextPane.setSelectionStart( nextPosn ); // posn cursor at word start
                jTextPane.setSelectionEnd( nextPosn + find.length() );
            }
        }
    }

    public int getLineNumber(JTextPane component, int pos)
    {
        int posLine;
        int y = 0;
        try
        {
            Rectangle caretCoords = component.modelToView(pos);
            y = (int) caretCoords.getY();
        }
        catch (Exception e)
        {
        }

        int lineHeight = component.getFontMetrics(component.getFont()).getHeight();
        posLine = (y / lineHeight) + 1;
        return posLine;
    }

    public int nextIndex(String input, String find, int start, boolean
            caseSensitive ) {
        int textPosn = -1;
        if ( input != null && find != null && start < input.length() ) {
            if ( caseSensitive == true ) { // indexOf() returns -1 if not found
                textPosn = input.indexOf( find, start );
            } else {
                textPosn = input.toLowerCase().indexOf( find.toLowerCase(),
                        start );
            }
        }
        return textPosn;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand()=="Cancel")
        {
            this.setVisible(false);
            this.dispose();

        }
    }
}

class MyDialog extends  Dialog implements  ActionListener
{
    private Label lbl_msg;
    private Label lbl_msg1;
    private Label lbl_msg2;
    private Label lbl_msg3;
    private Label lbl_msg4;
    private Label lbl_msg5;
    private Label lbl_msg6;
    private Label lbl_msg7;
    private Label lbl_msg8;
    private Label lbl_msg9;
    private Label lbl_msg10;

    private Button btn_yes;
    private Panel center_panel,south_panel;
    public MyDialog()
    {
        super(new MyMenuesBA(),"Dialog Demo",true);
        center_panel=new Panel();
        south_panel=new Panel();

        setTitle("How To Use");
        setSize(550,400);
        setLocation(50,80);
        lbl_msg=new Label("Click FILE to:                                                       ");
        lbl_msg1=new Label("                    1. Create a New .txt Document!");
        lbl_msg2=new Label("2. Open a .txt document in the available pane!");
        lbl_msg3=new Label("3. Save all opened Documents!");
        lbl_msg4=new Label("4. Exit the application!");
        lbl_msg5=new Label("Click EDIT to: ");
        lbl_msg6=new Label("1. Replace a supplied string with another supplied string in an open document!");
        lbl_msg7=new Label("2. take the user to a particular line number in an open document by pressing Goto!");
        lbl_msg8=new Label("Click FORMAT to: ");
        lbl_msg9=new Label("1.Display all the lines in the open file in the color green by clicking on Green!");
        lbl_msg10=new Label("2. Display all the lines in the open file in the color red by clicking on RED!");
        btn_yes=new Button("Exit");

        center_panel.add(lbl_msg);
        center_panel.add(lbl_msg1);
        center_panel.add(lbl_msg2);
        center_panel.add(lbl_msg3);
        center_panel.add(lbl_msg4);
        center_panel.add(lbl_msg5);
        center_panel.add(lbl_msg6);
        center_panel.add(lbl_msg7);
        center_panel.add(lbl_msg8);
        center_panel.add(lbl_msg9);
        center_panel.add(lbl_msg10);

        south_panel.add(btn_yes);
        add(south_panel,"South");
        add(center_panel,"Center");
        btn_yes.addActionListener(this);

    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==btn_yes)
            dispose();
    }
}

