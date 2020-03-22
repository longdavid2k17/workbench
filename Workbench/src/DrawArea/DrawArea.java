package DrawArea;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.*;


@SuppressWarnings("serial")
public class DrawArea extends JPanel
{
    private static final int BI_WIDTH = 1700;
    private static final int BI_HEIGHT = 800;
    private static final Color LABEL_DRAW_COLOR = new Color(180, 180, 255);
    private static final Stroke LABEL_DRAW_STROKE = new BasicStroke(1);
    private static final Stroke BIMAGE_DRAW_STROKE = new BasicStroke(3);
    private BufferedImage bImage = new BufferedImage(BI_WIDTH, BI_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private List<Point> pointList = new ArrayList<Point>();
    private JLabel imageLabel;
    private Color color = Color.BLACK;
    private boolean isRubberSelected = false;
    private boolean isAllowedToDraw = true;
    private JFrame framePointer;
    private File tempFile;

    private ServerSocket serverSocket = null;
    private OutputStream os = null;
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private boolean isReciving = false;
    private boolean isRecived = false;
    private String serverAddress;
    private int port = 6060;
    private Socket server;
    private Graphics2D g2d;

    int bytesRead;
    int current = 0;
    public static ArrayList<String> addressList = new ArrayList<>();

    public static void addToClientList(String arg)
    {
        addressList.add(arg);
    }

    public boolean isAllowedToDraw()
    {
        return isAllowedToDraw;
    }

    public void setAllowedToDraw(boolean allowedToDraw)
    {
        isAllowedToDraw = allowedToDraw;
    }

    public DrawArea(String serverAddress, boolean isAllowedToDraw, JFrame framePointer) throws IOException
    {
        setAllowedToDraw(isAllowedToDraw);
        this.framePointer = framePointer;
        this.serverAddress = serverAddress;

        g2d = bImage.createGraphics();
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, BI_WIDTH, BI_HEIGHT);
        g2d.dispose();

        serverSocket = new ServerSocket(41567);
        serverSocket.setSoTimeout(180000000);

        imageLabel = new JLabel(new ImageIcon(bImage))
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                paintInLabel(g);
            }
        };
        MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
        if(isAllowedToDraw()) {
            imageLabel.addMouseListener(myMouseAdapter);
            imageLabel.addMouseMotionListener(myMouseAdapter);

            new Thread()
            {
                @Override
                public void run()
                {
                    for(;;)
                    {
                        try
                        {
                            sleep(500);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            for(int i=0;i<addressList.size();i++)
                            {



                            //System.out.println("Długość listy adresów: "+addressList.size());
                                    //"192.168.8.176"
                                if(!addressList.get(i).equals(serverAddress))
                                {
                                    Socket client = new Socket(addressList.get(i), 41567);
                                    System.out.println("Nasłuch portów w celu wysłania do klienta ");
                                    tempFile = new File("blackbrd.jpg");
                                    ImageIO.write(bImage, "jpg", tempFile);
                                    System.out.println("Utworzono plik podglądowy!");
                                    ImageIO.write(bImage, "JPG", client.getOutputStream());
                                    System.out.println("Wysłano!");
                                }
                                else
                                {
                                    continue;
                                }
                           }
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(framePointer, e, framePointer.getTitle(), JOptionPane.ERROR_MESSAGE);
                            System.out.println(e.getMessage());
                            System.out.println(e.toString());
                        }
                    }
                }
            }.start();
        }
        else
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    for (;;)
                    {
                        try
                        {
                            sleep(500);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            Socket server = serverSocket.accept();
                            System.out.println("Połączono. Trwa aktualizacja");
                            BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(server.getInputStream()));
                            imageLabel.setIcon(new ImageIcon(img));
                            imageLabel.repaint();
                            framePointer.repaint();
                            System.out.println("Zaktualizowano");
                            server.close();
                        }
                        catch (SocketTimeoutException st)
                        {
                            System.out.println("Socket timed out!");
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        catch (Exception ex)
                        {
                            System.out.println(ex);
                        }
                    }
                }
            }.start();
        }
        add(imageLabel, BorderLayout.CENTER);
    }



    public void clear()
    {
        Graphics2D g2 = bImage.createGraphics();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, BI_WIDTH, BI_HEIGHT);
        g2.dispose();
        imageLabel.repaint();
    }

    private void paintInLabel(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(LABEL_DRAW_COLOR);
        g2d.setStroke(LABEL_DRAW_STROKE);
        if (pointList.size() < 2)
        {
            return;
        }
        for (int i = 1; i < pointList.size(); i++)
        {
            int x1 = pointList.get(i - 1).x;
            int y1 = pointList.get(i - 1).y;
            int x2 = pointList.get(i).x;
            int y2 = pointList.get(i).y;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private class MyMouseAdapter extends MouseAdapter
    {

        @Override
        public void mousePressed(MouseEvent e)
        {
            pointList.add(e.getPoint());
            imageLabel.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            Graphics2D g2d = bImage.createGraphics();
            g2d.setColor(color);
            g2d.setStroke(BIMAGE_DRAW_STROKE);
            if (pointList.size() >= 2)
            {
                for (int i = 1; i < pointList.size(); i++)
                {
                    int x1 = pointList.get(i - 1).x;
                    int y1 = pointList.get(i - 1).y;
                    int x2 = pointList.get(i).x;
                    int y2 = pointList.get(i).y;
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
            g2d.dispose();
            pointList.clear();
            imageLabel.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            pointList.add(e.getPoint());
            imageLabel.repaint();
        }
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void rubber()
    {
        if(!isRubberSelected)
        {
            isRubberSelected=true;
            setColor(Color.white);
        }
        else if(isRubberSelected)
        {
            isRubberSelected = false;
            setColor(Color.BLACK);
        }
    }

    public void setColor(JFrame pointer)
    {
        Color settedColor = JColorChooser.showDialog(pointer,"Wybierz kolor",Color.BLACK);
        color = settedColor;
    }

    public Color getColor()
    {
        return color;
    }
}