package parte2.football;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class TeamStats extends JFrame {

    ScoreBoard board = new ScoreBoard();

    private void graphics(){

        //Creating the MenuBar and adding components

        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Teams");
        JMenu m3 = new JMenu("List");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);

        JMenuItem F1 = new JMenuItem("Load");  //na imagem n parecem botoes e sim menus
        JMenuItem F2 = new JMenuItem("Save");
        JMenuItem F3 = new  JMenuItem("Exit");
        m1.add(F1);
        m1.add(F2);
        m1.add(F3);


        JMenuItem t1 = new JMenuItem("Team Information");
        JMenuItem t2 = new JMenuItem("Add game");
        m2.add(t1);
        m2.add(t2);

        JMenuItem List1 = new JMenuItem("Teams playing");
        JMenuItem List2 = new JMenuItem("Points");
        JMenuItem List3 = new JMenuItem("Points per team");
        m3.add(List1);
        m3.add(List2);
        m3.add(List3);

        setJMenuBar(mb);


        // ------------- MID ------------

        JPanel panel = new JPanel();

        Container container = getContentPane();

        container.setLayout(new BorderLayout());

        JTextArea textarea = new JTextArea(35, 70);
        textarea.setText("2\u00BA Trabalho PG III");
        JScrollPane scrollPane = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        container.add(scrollPane);
        //container.add(panel, BorderLayout.CENTER);

        pack();



        // ---------- BOTTOM --------------


        JButton inf = new JButton("Information");
        JButton teams = new JButton("Teams");
        JButton clear = new JButton("Clear");

        JPanel bottom = new JPanel();

        Container contain = getContentPane();
        contain.setLayout(new BorderLayout());
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT));

        bottom.add(inf);
        bottom.add(teams);
        bottom.add(clear);
        bottom.setVisible(true);

        contain.add(bottom, BorderLayout.SOUTH);

        //----------------- Events ----------------------


        F1.addActionListener((event) -> {

            try {
                FileReader reader = new FileReader("Output.txt");
                textarea.read(reader, "Output.txt");
                reader.close();

            } catch (Exception e) {

                System.err.println("An Error occurred");
            }
        });


        F2.addActionListener((event) -> {
            try {
                PrintWriter out = new PrintWriter(new FileWriter("Output.txt"));

                String text = textarea.getText();
                out.println(text);
                out.flush();

            } catch (Exception e) {
                System.err.println("An Error occurred");
            }


        });

        F3.addActionListener((event) ->{
            int n = JOptionPane.showConfirmDialog(mb,
                    "Are you sure you want to exit?", "Exit?",JOptionPane.YES_NO_OPTION);
            if(n == JOptionPane.YES_OPTION){
                System.exit(0);
                System.out.println("EXIT SUCCESSFUL");
            }
        });


        t1.addActionListener((event) -> {


        });

        t2.addActionListener((event) -> {

            String output = textarea.getText();

            try{



            }catch (Exception e) {

                System.err.println("An Error occurred");
            }



        });



        clear.addActionListener((event) -> {
            textarea.setText("");
        });


        teams.addActionListener((event) ->{

            textarea.setText("");


            try {
                board.saveOn("Tabela.txt");
                FileReader reader = new FileReader("Tabela.txt");
                textarea.read(reader, "Tabela.txt");
                reader.close();

            } catch (Exception e) {

                System.err.println("An Error occurred");
            }



        });

        inf.addActionListener((event) ->{

            try {
                board.saveOn("Tabela.txt");
                FileReader reader = new FileReader("Tabela.txt");
                textarea.read(reader, "Tabela.txt");
                reader.close();

            } catch (Exception e) {

                System.err.println("An Error occurred");
            }

        });

    }



    public TeamStats (){
        super( "Teams");
        graphics();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(736, 675);
        setResizable(false); //permite que o utilizador não possa alterar o tamanho da janela, isto foi feito pois se não tivessemos isto aparecia uma janela normal mas se quisessemos fazer fullscreen os componentes não acompanhavam
    }

    public static void main(String[] args) {
        TeamStats frame = new TeamStats();
        frame.setVisible(true);
    }

}
