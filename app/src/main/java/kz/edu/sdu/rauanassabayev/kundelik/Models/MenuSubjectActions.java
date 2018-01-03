package kz.edu.sdu.rauanassabayev.kundelik.Models;

/**
 * Created by rauanassabayev on 12/17/17.
 */

public class MenuSubjectActions {
    String text;
    int icon;
    public MenuSubjectActions(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public int getIcon() {
        return icon;
    }
    public void setIcon(int icon) {
        this.icon = icon;
    }
}
