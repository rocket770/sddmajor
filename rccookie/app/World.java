package rccookie.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;

public class World extends JPanel {

    private static final long serialVersionUID = -6045289675650450157L;

    private final ArrayList<Item> items = new ArrayList<>();


    public World(int width, int height) {
        setSize(width, height);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        ((Graphics2D)g).fillRect(getX(), getY(), getWidth(), getHeight());
        paintItems((Graphics2D)g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void paintItems(Graphics2D g) {
        for(Item item : items) {
            Image image = item.getImage();
            g.drawImage(image, (int)item.getX() - image.getWidth(this) / 2, (int)item.getY() - image.getHeight(this), this);
        }
    }




    public List<Item> getItems() {
        return List.copyOf(items);
    }

    @SuppressWarnings("unchecked")
    public <I extends Item> List<I> getItems(Class<I> cls) {
        return (List<I>)items.stream().filter(cls::isInstance).collect(Collectors.toList());
    }


    public void addItem(Item item) {
        items.add(item);
    }

    public void addItems(Collection<Item> items) {
        items.addAll(items);
    }


    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    public boolean removeItems(Collection<Item> items) {
        return items.removeAll(items);
    }
}
