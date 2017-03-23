package client;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.*;  
//
//import java.awt.*;              //for layout managers and more
//import java.awt.event.*;        //for action events
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


public class Demo extends JFrame{
    protected void initUI() {
        final JFrame frame = new JFrame();
        frame.setTitle(Demo.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JTextArea chat = new JTextArea(10, 40);
        chat.setLineWrap(true);
        chat.setEditable(false);
        chat.setWrapStyleWord(false);
        final JScrollPane scrollPane = new JScrollPane(chat);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        Timer t = new Timer(200, new ActionListener() {

            private int i = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                final Rectangle visibleRect = chat.getVisibleRect();
                boolean scroll = chat.getHeight() <= visibleRect.y + visibleRect.height;
                chat.append("Hello line " + i++ + "\n");
                if (!scroll) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            chat.scrollRectToVisible(visibleRect);
                        }
                    });
                }
            }
        });
        t.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Demo().initUI();
            }
        });
    }
}