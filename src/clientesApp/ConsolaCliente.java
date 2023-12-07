package clientesApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Consola.InterfazAdminLocal;
import Consola.VentanaInicial;
import Inventario.Inventario;
import SistemaAlquiler.*;

public class ConsolaCliente extends JFrame{
	private SistemaCliente sistema;
	private VehiculoRentalSystem rentalSystem;
	
	public ConsolaCliente() {
		
		sistema = new SistemaCliente();
		rentalSystem = sistema.getRentalSystem();
		
		setTitle("Aplicación Cliente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setSize(400, 200);
		setResizable(false);
      
		getContentPane().removeAll();

        JPanel myPanel = new JPanel(new GridLayout(4, 5));

        JButton iniciarSesion = new JButton("Iniciar Sesión");
        JButton registrarse = new JButton("Regístrate aquí");
		JLabel texto1 = new JLabel("Bienvenido");
		JLabel texto2 = new JLabel("¿Qué desea hacer?");
		texto1.setHorizontalAlignment(JLabel.CENTER);
		texto2.setHorizontalAlignment(JLabel.CENTER);

		myPanel.add(texto1);
		myPanel.add(texto2);
		myPanel.add(iniciarSesion);
        myPanel.add(registrarse);
        
        add(myPanel);
        iniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	mostrarInicioSesion();
            }
        });
        
        
        registrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();
            	mostrarRegistro();
            }
        });
        
        
	}
	
	private void mostrarRegistro() {
		setTitle("Registrarse ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        JPanel myPanel = new JPanel(new GridLayout(5, 2));
        getContentPane().add(myPanel);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        JTextField usuario = new JTextField();
        JTextField contrasenia = new JTextField();

        myPanel.add(new JLabel("Ingrese su nuevo nombre de usuario"));
        myPanel.add(usuario);
        myPanel.add(new JLabel("Ingrese su nueva contraseña"));
        myPanel.add(contrasenia);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ingrese los datos de registro", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String usuarioTexto = usuario.getText();
            String contraseniaTexto = contrasenia.getText();
            Cliente newCliente = sistema.RegistarCliente(usuarioTexto, contraseniaTexto);
            if (newCliente != null) {
	            mostrarOpciones(newCliente);
	            dispose();
            }
        }
        setVisible(true);
	}
	
	private void mostrarInicioSesion() {
		setTitle("Inicio Sesión ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        JPanel myPanel = new JPanel(new GridLayout(2, 2));
        getContentPane().add(myPanel);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        JTextField usuario = new JTextField();
        JTextField contrasenia = new JTextField();

        myPanel.add(new JLabel("Ingrese su nombre de usuario"));
        myPanel.add(usuario);
        myPanel.add(new JLabel("Ingrese su contraseña"));
        myPanel.add(contrasenia);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ingrese los datos de inicio de sesión", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String usuarioTexto = usuario.getText();
            String contraseniaTexto = contrasenia.getText();
            Cliente newCliente = sistema.validacionCliente(usuarioTexto, contraseniaTexto);
            if (newCliente != null) {
	            mostrarOpciones(newCliente);
	            dispose();
            }
        }
        setVisible(true);
    }
	
	

	private void mostrarOpciones(Cliente cliente) {
		getContentPane().removeAll();
		final Cliente newCliente = cliente;
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(5,5,5,5); // Márgenes entre botones

        JButton botonReserva = new JButton("Reservar Vehiculo");
        JButton botonDisponibilidad = new JButton("Ver disponibilidad vehículos");
        JButton botonSalir = new JButton("Salir");

        add(botonReserva, gbc);
        add(botonDisponibilidad, gbc);
        add(botonSalir, gbc);
        
        
        botonReserva.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistema.opcion1Cliente(newCliente);
            }
        });
        botonDisponibilidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistema.opcion2Cliente();            }
         });
        
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
         });
	}
	
	public static void main(String[] args) {
        ConsolaCliente ventana = new ConsolaCliente();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }
}
