/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editor;

import editor.action.*;
import editor.display.CharacterDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Editor is the main class of the editor application. It is mainly responsible
 * for creating the document and display models, and to connect them up.
 *
 * @author evenal
 */
public class Editor extends JFrame {

    Document doc;
    private InputMap inputMap;
    private ActionMap actionMap;
    private CharacterDisplay display;

    public Editor() throws HeadlessException {
        super("Simple Text Editor");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        display = new CharacterDisplay();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(display, BorderLayout.CENTER);

        /**
         * The inputMap and actionMap determine what happens when the user
         * presses a key on the keyboard. The keys are not hard-coded to the
         * actions. The keyboard is
         */
        inputMap = display.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = display.getActionMap();
        addKeyMappings();

        pack();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        // TODO code application logic here

        Editor editor = new Editor();
        editor.setVisible(true);

        editor.doc = new Document(editor.display);
    }

    protected void exit() {
        for (java.awt.Window win : java.awt.Dialog.getWindows()) {
            win.dispose();
        }
        for (java.awt.Frame frame : java.awt.Frame.getFrames()) {
            frame.dispose();
        }
    }

    /**
     * Add a key mapping, which binds an action to a particular key (represented
     * by the keyStroke). Whenever the key is pressed, the actionPerformed()
     * method in the action will be called
     *
     * @param keyStroke key to bind
     * @param action    action to bind the key to
     */
    public void addKeyMapping(KeyStroke keyStroke, EditorAction action) {
        inputMap.put(keyStroke, action.getName());
        actionMap.put(action.getName(), action);
    }


    public void addKeyMappings() {
        String os = System.getProperty("os.name");
        System.out.println(os);
        inputMap.clear();
        actionMap.clear();
        char ch;
        for (ch = 0x000; ch <= 0x007F; ch++) {
            if (os.toLowerCase().matches("windows 10")) {
                if (ch == 0x007F) {
                    String name = "removeChar";
                     KeyStroke backspace = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,0,false);
                    EditorAction remove = new RemoveAction(name, this);
                    addKeyMapping(backspace, remove);
                } else if (System.getProperty("os.name").toLowerCase().matches("mac os x")) {
                    if (ch == 0x008 || ch == 0x007F) {
                        String name = "removeChar";
                        EditorAction remove = new RemoveAction(name, this);
                        addKeyMapping(KeyStroke.getKeyStroke(ch), remove);
                    }
                } else if (ch == '\n') {
                    String name = "shiftChar";
                    EditorAction lineShift = new LineshiftAction(name, this);
                    addKeyMapping(KeyStroke.getKeyStroke(ch), lineShift);
                } else if (ch == '\t') {
                    String name = "moveUp";
                    EditorAction moveUp = new MovingUpAction(name, this);
                    addKeyMapping(KeyStroke.getKeyStroke(ch), moveUp);
                } else {
                    if (ch != 0x008) {
                        String name = "insertChar";
                        EditorAction action = new InsertAction(name, this);
                        addKeyMapping(KeyStroke.getKeyStroke(ch), action);
                    }
                }
            }
        }
    }

    public CharacterDisplay getDisplay() {
        return display;
    }

    public Document getDocument() {
        return doc;
    }
}
