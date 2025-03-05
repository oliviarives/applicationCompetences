import controleur.NavigationControleur;
import vue.NavigationView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainApplication {
    public static void main(String[] args) throws SQLException {

        NavigationView navigationView = new NavigationView();
        NavigationControleur navigationController = new NavigationControleur(navigationView);

        navigationView.setVisible(true);


    }
}
