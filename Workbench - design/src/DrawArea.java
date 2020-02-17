import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DrawArea extends JComponent
{
    private Image image;
    private Graphics2D g2;
    private int currentX, currentY, oldX, oldY;
    boolean isRubberSelected = true;
    boolean isAdmin = false;

    Color color;

    private boolean isTextSelected = false;

    public DrawArea(boolean isAdmin)
    {
        BufferedImage buffImg = new BufferedImage(1700, 800, BufferedImage.TYPE_INT_RGB);
        g2 = buffImg.createGraphics();
        File filestream = new File("blackbrd.png");

        setAdmin(isAdmin);
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                oldX = e.getX();
                oldY = e.getY();
                if(isTextSelected)
                {
                    g2.drawString("Test",oldX,oldY);
                    isTextSelected=false;
                }
            }
        });

                addMouseMotionListener(new MouseMotionAdapter()
                {
                    public void mouseDragged(MouseEvent e)
                    {
                        currentX = e.getX();
                        currentY = e.getY();
                        if (g2 != null)
                        {
                            g2.drawLine(oldX, oldY, currentX, currentY);
                            repaint();

                            oldX = currentX;
                            oldY = currentY;
                            /*try
                            {
                                ImageIO.write(buffImg, "png", filestream);
                            }
                            catch (IOException e2)
                            {
                                e2.printStackTrace();
                            }*/
                        }
                    }
                });
    }


    public void setAdmin(boolean value)
    {
        isAdmin = value;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    protected void paintComponent(Graphics g)
    {
        if (image == null)
        {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
    }

    public void clear()
    {
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public void getDrawArea()
    {
        if(!isAdmin())
        {
            return;
        }
        File tempImage = new File("blackbrd.jpg");
        try
        {
            image = ImageIO.read(tempImage);
            g2 = (Graphics2D) image.getGraphics();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void rubber()
    {
        if(!isRubberSelected)
        {
            isRubberSelected=true;
            g2.setPaint(Color.white);
        }
        else if(isRubberSelected)
        {
            isRubberSelected = false;
            g2.setColor(color);
        }
    }

    public void setColor(JFrame pointer)
    {
        Color settedColor = JColorChooser.showDialog(pointer,"Wybierz kolor",Color.BLACK);
        g2.setPaint(settedColor);
        color = settedColor;
    }
    public void text()
    {
        isTextSelected = true;
    }
}