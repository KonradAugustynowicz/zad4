import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class PicturePanel extends JPanel {
    BufferedImage image;
    int[][][] pixels;

    public PicturePanel() throws IOException {
        setSize(1000, 1000);
        image = ImageIO.read((Objects.requireNonNull(getClass().getResource("apple_noise.png"))));
        JLabel label = new JLabel("", new ImageIcon(image), 0);
        pixels = new int[image.getHeight()][image.getWidth()][3];
    }

    public void displayImage(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.displayImage(g);
    }

    public void loadPicture() {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                Color c = new Color(color);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;

                pixels[i][j][0] = r;
                pixels[i][j][1] = g;
                pixels[i][j][2] = b;
                image.setRGB(i, j, c.getRGB());
            }
        }
    }

    public void additiveIncrease(int red, int green, int blue) throws IOException {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                Color c = new Color(color);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;
                c = this.validateColor(r + red, g + green, b + blue);
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void subtractiveIncrease(int red, int green, int blue) throws IOException {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                Color c = new Color(color);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;
                c = this.validateColor(r - red, g - green, b - blue);
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void dividiveIncrease(int red, int green, int blue) throws IOException {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                Color c = new Color(color);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;
                c = this.validateColor(red != 0 ? r / red : r, green != 0 ? g / green : g, blue != 0 ? b / blue : b);
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void multiplicativeIncrease(int red, int green, int blue) throws IOException {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;
                Color c = this.validateColor(r * red, g * green, b * blue);
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void brightnessChange(float percentage) throws IOException {
        int r = 0;
        int g = 0;
        int b = 0;
        percentage = percentage / 100;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;
                Color c = this.validateColor((int) (r * percentage), (int) (g * percentage), (int) (b * percentage));
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void grayScale() throws IOException {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;
                int avg = (r + g + b) / 3;
                Color c = this.validateColor(avg, avg, avg);
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void grayScaleByRed() {
        int r = 0;
        int g = 0;
        int b = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                b = color & 0xff;
                g = (color & 0xff00) >> 8;
                r = (color & 0xff0000) >> 16;

                Color c = this.validateColor(r, r, r);
                image.setRGB(i, j, c.getRGB());
            }
        }
        Graphics graphics = getGraphics();
        graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    public void smoothingFilter() {
        int r = 0;
        int g = 0;
        int b = 0;
        loadPicture();
        int filteredPixels[][][] = new int[image.getWidth()][image.getHeight()][3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                filteredPixels[i][j] = smoothen(i, j);
                Color c = this.validateColor(filteredPixels[i][j][0], filteredPixels[i][j][1], filteredPixels[i][j][2]);
                image.setRGB(i, j, c.getRGB());
            }
        }
        paintComponent(getGraphics());
    }

    private int[] smoothen(int x, int y) {
        int elementsCount = 0;
        int[] average = new int[3];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    average[0] += pixels[x + i][y + j][0];
                    average[1] += pixels[x + i][y + j][1];
                    average[2] += pixels[x + i][y + j][2];
                    elementsCount++;

                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        average[0] = average[0] / elementsCount;
        average[1] = average[1] / elementsCount;
        average[2] = average[2] / elementsCount;
        return average;
    }

    public void medianaFilter() {
        loadPicture();
        int[][][] filteredPixels = new int[image.getWidth()][image.getHeight()][3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                filteredPixels[i][j] = medianate(i, j);

                Color c = this.validateColor(filteredPixels[i][j][0], filteredPixels[i][j][1], filteredPixels[i][j][2]);
                image.setRGB(i, j, c.getRGB());
            }
        }
        paintComponent(getGraphics());
    }

    public int[] medianate(int x, int y) {
        LinkedList<Integer> medianaList = new LinkedList<>();
        HashMap<Integer, int[]> map = new HashMap<>();
        int elementsCount = 0;
        int[] average = new int[3];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    medianaList.add((pixels[x + i][y + j][0] + pixels[x + i][y + j][1] + pixels[x + i][y + j][2]) / 3);

                    average[0] += pixels[x + i][y + j][0];
                    average[1] += pixels[x + i][y + j][1];
                    average[2] += pixels[x + i][y + j][2];
                    map.put((pixels[x + i][y + j][0] + pixels[x + i][y + j][1] + pixels[x + i][y + j][2]) / 3,
                            new int[]{pixels[x + i][y + j][0], pixels[x + i][y + j][1], pixels[x + i][y + j][2]});
                    elementsCount++;
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        Collections.sort(medianaList);
        if (elementsCount%2 == 1){
            return map.get(medianaList.get(elementsCount / 2 +1));
        }else{
            return map.get(medianaList.get(elementsCount / 2 ));
        }
    }

    public void sopelFilter(){
        loadPicture();
        int[][][] filteredPixels = new int[image.getWidth()][image.getHeight()][3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                filteredPixels[i][j] = sopelization(i, j);

                Color c = this.validateColor(filteredPixels[i][j][0], filteredPixels[i][j][1], filteredPixels[i][j][2]);
                image.setRGB(i, j, c.getRGB());
            }
        }
        paintComponent(getGraphics());
    }

    public int[] sopelization(int x, int y){
        int[] average = new int[3];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    if (i==-1 && j==-1){
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i==0 && j==-1){
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i==1 && j==-1){
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i==-1 && j==1){
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                    } else if (i==0 && j==1){
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                    } else if (i==1 && j==1){
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                    }

                    if (i==-1 && j==-1){
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i==-1 && j==0){
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i==-1 && j==1){
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i==1 && j==-1){
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                    } else if (i==1 && j==0){
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                    } else if (i==1 && j==1){
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                    }
                } catch (IndexOutOfBoundsException e) {
                    return new int[]{0,0,0};
                }
            }
        }
        return average;
    }

    public void sharpenerFilter(){
        loadPicture();
        int[][][] filteredPixels = new int[image.getWidth()][image.getHeight()][3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                filteredPixels[i][j] = sharpen(i, j);

                Color c = this.validateColor(filteredPixels[i][j][0], filteredPixels[i][j][1], filteredPixels[i][j][2]);
                image.setRGB(i, j, c.getRGB());
            }
        }
        paintComponent(getGraphics());
    }

    public int[] sharpen(int x, int y){
        int[] average = new int[3];
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    if (i == -1 && j == -1) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == 0 && j == -1) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == 1 && j == -1) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == -1 && j == 0) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == 0 && j == 0) {
                        average[0] += pixels[x + i][y + j][0]*9;
                        average[1] += pixels[x + i][y + j][1]*9;
                        average[2] += pixels[x + i][y + j][2]*9;
                    } else if (i == 1 && j == 0) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == -1 && j == 1) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == 0 && j == 1) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    } else if (i == 1 && j == 1) {
                        average[0] -= pixels[x + i][y + j][0];
                        average[1] -= pixels[x + i][y + j][1];
                        average[2] -= pixels[x + i][y + j][2];
                    }
                } catch (IndexOutOfBoundsException e) {
                    return new int[]{0, 0, 0};
                }
            }
        }
        return average;
    }

    public void gaussFilter(){
        loadPicture();
        int filteredPixels[][][] = new int[image.getWidth()][image.getHeight()][3];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                filteredPixels[i][j] = gauss(i, j);

                Color c = this.validateColor(filteredPixels[i][j][0], filteredPixels[i][j][1], filteredPixels[i][j][2]);
                image.setRGB(i, j, c.getRGB());
            }
        }
        paintComponent(getGraphics());
    }

    public int[] gauss(int x, int y){
        int elementsCount = 0;
        int[] average = new int[3];
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                try {
                    if (i == -1 && j == -1) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == 0 && j == -1) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == 1 && j == -1) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == -1 && j == 0) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == 0 && j == 0) {
                        average[0] += pixels[x + i][y + j][0]*5;
                        average[1] += pixels[x + i][y + j][1]*5;
                        average[2] += pixels[x + i][y + j][2]*5;
                        elementsCount+=5;
                    } else if (i == 1 && j == 0) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == -1 && j == 1) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == 0 && j == 1) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == 1 && j == 1) {
                        average[0] += pixels[x + i][y + j][0]*2;
                        average[1] += pixels[x + i][y + j][1]*2;
                        average[2] += pixels[x + i][y + j][2]*2;
                        elementsCount+=2;
                    } else if (i == -2 && j == 0) { // nie środek
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                        elementsCount++;
                    } else if (i == 2 && j == 0) {
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                        elementsCount++;
                    } else if (i == 0 && j == -2) {
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                        elementsCount++;
                    } else if (i == 0 && j == 2) {
                        average[0] += pixels[x + i][y + j][0];
                        average[1] += pixels[x + i][y + j][1];
                        average[2] += pixels[x + i][y + j][2];
                        elementsCount++;
                    }
                } catch (IndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        average[0] /= elementsCount;
        average[1] /= elementsCount;
        average[2] /= elementsCount;
        return average;
    }

    public void reset() throws IOException {
        image = ImageIO.read((Objects.requireNonNull(getClass().getResource("apple_noise.png"))));
        paintComponent(getGraphics());
    }

    public Color validateColor(int red, int green, int blue) {
        if (red >= 255) {
            red = 255;
        } else if (red < 0) {
            red = 0;
        }
        if (green >= 255) {
            green = 255;
        } else if (green < 0) {
            green = 0;
        }
        if (blue >= 255) {
            blue = 255;
        } else if (blue < 0) {
            blue = 0;
        }
        return new Color(red, green, blue);
    }
}
