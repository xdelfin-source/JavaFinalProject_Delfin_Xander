import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.Font;

public class Chordgen {

    public static void main(String[] args) {

        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 20));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));
        UIManager.put("TextField.font", new Font("Arial", Font.PLAIN, 20));

        while (true) {
            String inputRoot = JOptionPane.showInputDialog("Enter Root Note (e.g., C, C#, D):");

            if (inputRoot == null) break;

            if (!Chord.isValid(inputRoot)) {
                JOptionPane.showMessageDialog(null, "Invalid Note! Choose from:\n" + Chord.getAllNotes());
                continue;
            }

            int type = 0;
            while (true) {
                String typeStr = JOptionPane.showInputDialog("Enter Type:\n1 - Major\n2 - Minor");

                if (typeStr == null) break;

                if (typeStr.equals("1")) {
                    type = 1;
                    break;
                } else if (typeStr.equals("2")) {
                    type = 2;
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid choice! Please enter 1 for Major or 2 for Minor.");
                }
            }

            if (type == 0) break;

            Chord myChord = new Chord(inputRoot, type);
            JOptionPane.showMessageDialog(null, myChord.generate());

            int choice = JOptionPane.showConfirmDialog(null, "Create another?", "ChordGen", JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) break;
        }
    }
}

class Chord {
    private final String root;
    private final int type;

    private static final String[] notes = {
            "C", "C#", "D", "D#", "E", "F",
            "F#", "G", "G#", "A", "A#", "B"
    };

    public Chord(String root, int type) {
        this.root = normalizeNote(root.trim().toUpperCase());
        this.type = type;
    }

    private String normalizeNote(String note) {
        if (note.equals("BB")) return "A#";
        if (note.equals("EB")) return "D#";
        if (note.equals("AB")) return "G#";
        if (note.equals("DB")) return "C#";
        if (note.equals("GB")) return "F#";
        return note;
    }

    public static boolean isValid(String input) {
        if (input == null) return false;
        String search = input.trim().toUpperCase();
        if (search.endsWith("B") && search.length() > 1) {
            return true;
        }
        for (String note : notes) {
            if (note.equals(search)) return true;
        }
        return false;
    }

    public static String getAllNotes() {
        StringBuilder sb = new StringBuilder();
        for (String note : notes) {
            sb.append(note).append(" ");
        }
        return sb.toString();
    }

    public String generate() {
        int rootIndex = 0;
        for (int i = 0; i < notes.length; i++) {
            if (notes[i].equals(this.root)) {
                rootIndex = i;
                break;
            }
        }

        int gap = (this.type == 1) ? 4 : 3;
        String name = (this.type == 1) ? "Major" : "Minor";

        String n1 = notes[rootIndex];
        String n2 = notes[(rootIndex + gap) % 12];
        String n3 = notes[(rootIndex + 7) % 12];

        return "Chord: " + n1 + " " + name + "\nNotes: " + n1 + " - " + n2 + " - " + n3;
    }
}