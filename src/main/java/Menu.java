import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu extends JMenuBar implements ActionListener {
    RgbInputPanel rgbInputPanel = new RgbInputPanel();
    Frame frame;
    JMenu filterMenu;
    JMenu menu;
    PicturePanel pp;

    int brightness = 0;

    JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13;

    public Menu(PicturePanel pp, Frame frame) {
        this.frame = frame;
        this.pp = pp;
        filterMenu = new JMenu("Filtr");
        i1 = new JMenuItem("Wygładzający");
        i2 = new JMenuItem("Medianowy");
        i3 = new JMenuItem("Sobela");
        i4 = new JMenuItem("Wyostrzący");
        i5 = new JMenuItem("Gaussa");

        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        i5.addActionListener(this);


        filterMenu.add(i1);
        filterMenu.add(i2);
        filterMenu.add(i3);
        filterMenu.add(i4);
        filterMenu.add(i5);

        this.add(filterMenu);

        menu = new JMenu("Przekształcenie punktowe");

        i6 = new JMenuItem("Dodawanie");
        i7 = new JMenuItem("Odejmowanie");
        i8 = new JMenuItem("Mnożenie");
        i9 = new JMenuItem("Dzielenie");
        i10 = new JMenuItem("Zmiana jasności(%)");
        i11 = new JMenuItem("GrayScaleAvg");
        i12 = new JMenuItem("GrayScaleR");

        i6.addActionListener(this);
        i7.addActionListener(this);
        i8.addActionListener(this);
        i9.addActionListener(this);
        i10.addActionListener(this);
        i11.addActionListener(this);
        i12.addActionListener(this);

        menu.add(i6);
        menu.add(i7);
        menu.add(i8);
        menu.add(i9);
        menu.add(i10);
        menu.add(i11);
        menu.add(i12);

        this.add(menu);

        i13 = new JMenuItem("Reset");

        i13.addActionListener(this);

        this.add(i13);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == i1)
            pp.smoothingFilter();
        else if (e.getSource() == i2)
            pp.medianaFilter();
        else if (e.getSource() == i3)
            pp.sopelFilter();
        else if (e.getSource() == i4)
            pp.sharpenerFilter();
        else if (e.getSource() == i5)
            pp.gaussFilter();
        else if (e.getSource() == i6) {
            JOptionPane.showConfirmDialog(null, rgbInputPanel,
                    "Podaj wartości kolorów", JOptionPane.OK_CANCEL_OPTION);
            try {
                pp.additiveIncrease(
                        Integer.parseInt(rgbInputPanel.r.getText()),
                        Integer.parseInt(rgbInputPanel.g.getText()),
                        Integer.parseInt(rgbInputPanel.b.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == i7) {
            JOptionPane.showConfirmDialog(null, rgbInputPanel,
                    "Podaj wartości kolorów", JOptionPane.OK_CANCEL_OPTION);
            try {
                pp.subtractiveIncrease(
                        Integer.parseInt(rgbInputPanel.r.getText()),
                        Integer.parseInt(rgbInputPanel.g.getText()),
                        Integer.parseInt(rgbInputPanel.b.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == i8) {
            JOptionPane.showConfirmDialog(null, rgbInputPanel,
                    "Podaj wartości kolorów", JOptionPane.OK_CANCEL_OPTION);
            try {
                pp.multiplicativeIncrease(
                        Integer.parseInt(rgbInputPanel.r.getText()),
                        Integer.parseInt(rgbInputPanel.g.getText()),
                        Integer.parseInt(rgbInputPanel.b.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == i9) {
            JOptionPane.showConfirmDialog(null, rgbInputPanel,
                    "Podaj wartości kolorów", JOptionPane.OK_CANCEL_OPTION);
            try {
                pp.dividiveIncrease(
                        Integer.parseInt(rgbInputPanel.r.getText()),
                        Integer.parseInt(rgbInputPanel.g.getText()),
                        Integer.parseInt(rgbInputPanel.b.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == i10) {
            brightness = Integer.parseInt(JOptionPane.showInputDialog(frame, "Podaj wartości kolorów"));
            try {
                pp.brightnessChange(brightness);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == i11) {
            try {
                pp.grayScale();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == i12) {
            pp.grayScaleByRed();
        } else if (e.getSource() == i13) {
            try {
                pp.reset();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
