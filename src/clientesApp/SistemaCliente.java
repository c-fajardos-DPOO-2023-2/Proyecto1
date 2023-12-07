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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import Consola.InterfazAdminLocal;
import SistemaAlquiler.*;
import Inventario.Inventario; 

public class SistemaCliente extends JFrame{
	
	final public Inventario inventario;
	private VehiculoRentalSystem rentalSystem;
	
	public SistemaCliente() {
		inventario = new Inventario();
		rentalSystem = inventario.getAplicacion();
	}
	
	public VehiculoRentalSystem getRentalSystem() {
		return rentalSystem;
	}
	
	
	public  Cliente RegistarCliente(String login, String contrasena) {
    	Cliente newCliente= rentalSystem.RegistarCliente(login, contrasena);
    	return newCliente;
	}
	
	public int cantidadDiasRenta(Date startDate, Date endDate) {
    	int diasRenta = rentalSystem.cantidadDiasRenta(startDate, endDate);
        return diasRenta;
    }
	
	public  int DeterminarTarifa(Date startDate, String categoriaInput) {
    	int tarifa = rentalSystem.DeterminarTarifa(startDate, categoriaInput);
        return tarifa;
    }
	
	
	public void escribirReserva(Reserva reserva) {
    	rentalSystem.escribirReserva(reserva);
    }
	
	public void addAgendasCarros(String IdCarro, AgendaCarro agendaCarro) {
    	rentalSystem.addAgendasCarros(IdCarro, agendaCarro);
    } 
	
	public void escribirAgendasCarros(String IdCarro, AgendaCarro agendaCarro) {
    	rentalSystem.escribirAgendasCarros(IdCarro, agendaCarro);
    }
	
	private void generarReserva(String categoriaInput, Date inicioDate, Date finDate, Cliente newCliente,
            Vehiculo selectedcarro, String NombreSedeRecoger, String NombreSedeDevolver, int diasRenta, int tarifa) {
	 	final Date startDate = inicioDate;
	 	final Date endDate = finDate;
	 	final Vehiculo selectedCar = selectedcarro;
        Reserva reserva = new Reserva(categoriaInput, startDate, endDate, newCliente.getName(),
                selectedCar.getVehiculoId(), NombreSedeRecoger, NombreSedeDevolver, "vigente");
        Categoria categoria = rentalSystem.getCategorias().get(categoriaInput);
        final double[] totalPrice = {reserva.getPrecio(diasRenta, tarifa, categoria.getvalorSedeDiferente())};

        // Crear variables finales efectivas
        final Reserva reservaFinal = reserva;
        final int diasRentaFinal = diasRenta;

        dispose();
        setTitle("Aquí informacion Alquiler");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().removeAll();
        JPanel ElPanel = new JPanel(new GridLayout(8, 2));
        getContentPane().add(ElPanel);
        ElPanel.add(new JLabel("Nombre del Cliente: " + newCliente.getName()));
        ElPanel.add(new JLabel("Vehículo: " + selectedCar.getmarca() + " " + selectedCar.getmodelo()));
        ElPanel.add(new JLabel("Dias del Alquiler: " + diasRenta));
        ElPanel.add(new JLabel("Precio total: " + totalPrice[0]));

        JButton botonConfirmar = new JButton("Confirmar el alquiler");
        JButton botonCancelar = new JButton("Cancelar alquiler");
        ElPanel.add(botonConfirmar);
        ElPanel.add(botonCancelar);

        // Mostrar la ventana principal después de configurar completamente la interfaz
        setVisible(true);

        botonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otras acciones...
                totalPrice[0] = rentalSystem.seleccionarSeguros(reservaFinal, totalPrice[0], diasRentaFinal);
                reservaFinal.setPrecio(totalPrice[0]*0.1);
                rentalSystem.addReserva(reservaFinal);
                rentalSystem.escribirReserva(reservaFinal);
                AgendaCarro indisponibilidad = new AgendaCarro(startDate, endDate, "Reservado");
                rentalSystem.addAgendasCarros(selectedCar.getVehiculoId(), indisponibilidad);
                rentalSystem.escribirAgendasCarros(selectedCar.getVehiculoId(), indisponibilidad);
                JOptionPane.showMessageDialog(null, "Se le hizo un descuento del 10% y se le descontó de su tarjeta $" + reservaFinal.getPrecioAbonado());
                JOptionPane.showMessageDialog(null, "Alquiler realizado exitosamente");
                // Otras acciones...
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Alquiler cancelado exitosamente");
                System.exit(0);
            }
        });
    }
	
	public  Cliente validacionCliente(String clienteUsername, String clientePassword) {
		// Menú para el cliente
		Cliente newCliente = null;
        boolean clienteAutenticado = false;
        
        while (!clienteAutenticado) {
            // Verificar si el usuario ya existe en el mapa
            if (rentalSystem.getContraseñasUsuarios().containsKey(clienteUsername)) {
                // Verificar si la contraseña coincide
                if (rentalSystem.getContraseñasUsuarios().get(clienteUsername).equals(clientePassword)) {
                	for (Cliente cliente : rentalSystem.getClientes().values()) {
                		if (cliente.getLogin().equals(clienteUsername)) {
                			newCliente = cliente;
                		}
                	}
                    clienteAutenticado = true;
                } else {
                	JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
                }
            } else {
            	JOptionPane.showMessageDialog(null, "Cliente no encontrado, registrese por favor");
            	dispose();
        		setTitle("Registrarse ");
        		setDefaultCloseOperation(EXIT_ON_CLOSE);
        		setLocationRelativeTo(null);
        		
        		JPanel myPanel = new JPanel(new GridLayout(3, 2));
        		
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
     	            newCliente = RegistarCliente(usuarioTexto,contraseniaTexto);
     	            clienteAutenticado = true;
     		 }  else {
     			System.exit(0);
     		 }
        		 setVisible(true);
            }
        }
        return newCliente;
	}
	
	
	public void opcion1Cliente( Cliente newCliente) {
		JPanel myPanel = new JPanel(new GridLayout(8, 2));
			
		getContentPane().add(myPanel);
        String[] categoriasOpciones = {"SUV", "pequeño", "van", "lujoso"};	
        JComboBox<String> categoriasComboBox = new JComboBox<>(categoriasOpciones);
		myPanel.add(new JLabel("Seleccione la categoría que desea alquilar "));
		myPanel.add(categoriasComboBox);
			
		JTextField fechaInicio = new JTextField();
		myPanel.add(new JLabel("Ingrese la fecha de inicio de renta (dd/MM/yyyy): "));
		myPanel.add(fechaInicio);			
		JTextField horaInicio = new JTextField();
		myPanel.add(new JLabel("Ingrese la hora de inicio de renta (HH/mm): "));
		myPanel.add(horaInicio);
		JTextField fechaFinal = new JTextField();
		myPanel.add(new JLabel("Ingrese la fecha final de renta (dd/MM/yyyy): "));
		myPanel.add(fechaFinal);
		JTextField horaFinal = new JTextField();
		myPanel.add(new JLabel("Ingrese la hora de fin de renta (HH/mm): "));
		myPanel.add(horaFinal);
		String[] sedesOpciones = {"Sucursal Central", "Sucursal Norte", "Sucursal Sur"};
		JComboBox<String> sedesRecoComboBox = new JComboBox<>(sedesOpciones);
	    JComboBox<String> sedesDevoComboBox = new JComboBox<>(sedesOpciones);
	    myPanel.add(new JLabel("Seleccione la sede en la que va a recoger el carro:"));
	    myPanel.add(sedesRecoComboBox);
	    myPanel.add(new JLabel("Seleccione la sede en la que va a devolver el carro:"));
	    myPanel.add(sedesDevoComboBox);
	        
	    int result = JOptionPane.showConfirmDialog(null, myPanel,
	                "Seleccione el seguro", JOptionPane.OK_CANCEL_OPTION);
	        
	    if (result == JOptionPane.OK_OPTION) {
	    	
		    try {
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy/HH/mm");
		        Date startDate = dateFormat.parse(fechaInicio.getText() + "/" + horaInicio.getText());
		        Date endDate = dateFormat.parse(fechaFinal.getText() + "/" + horaFinal.getText());
		        
		        List<Vehiculo> availableCarsIncategoria = new ArrayList<>();
		        for (Sede sede : rentalSystem.getSedes().values()) {
		        	if (sede.getNombre().equals((String)sedesRecoComboBox.getSelectedItem()));{
		        		for (Vehiculo car : sede.getVehiculos()) {
		        			String categoria = car.getCategoria();
		        			if (car.ValidarDisponibilidad(startDate, endDate) && categoria.equals((String)categoriasComboBox.getSelectedItem())) {
		    	            		availableCarsIncategoria.add(car);
		    	            }
		    	                
		    	        }
		    		}
		    	}
	            if (!availableCarsIncategoria.isEmpty()) {
	                // Seleccionar un carro aleatorio de la categoría
	                Random random = new Random();
	                Vehiculo selectedCar = availableCarsIncategoria.get(random.nextInt(availableCarsIncategoria.size()));
	                
	                // Calcular la diferencia de días entre las fechas
	                int diasRenta = rentalSystem.cantidadDiasRenta(startDate, endDate);

	                // Determinar si estamos en temporada alta (por ejemplo, de julio a diciembre)
	                int tarifa = rentalSystem.DeterminarTarifa(startDate, (String)categoriasComboBox.getSelectedItem());
	                
	                boolean reservaVigente = false;
	                if(rentalSystem.getReservas().size() != 0) {
	                	for(Reserva res: rentalSystem.getReservas()) {
	                    	if(res.getCliente().equals(newCliente.getName())&& res.getEstado().equals("vigente")) {
	                    		reservaVigente = true;
	                    	}
	                	}
	                    if(!reservaVigente) {
	                    	generarReserva((String)categoriasComboBox.getSelectedItem(), startDate, endDate, newCliente, selectedCar, 
	                    			(String)sedesRecoComboBox.getSelectedItem(), (String)sedesDevoComboBox.getSelectedItem(), diasRenta, tarifa);
	                    }else {
	                    	JOptionPane.showMessageDialog(null, "Ya tiene una reserva vigente, no puede generar otra reserva");
	                    	dispose();
	                    }
	                }else {
	                	generarReserva((String)categoriasComboBox.getSelectedItem(), startDate, endDate, newCliente, selectedCar, 
	                			(String)sedesRecoComboBox.getSelectedItem(), (String)sedesDevoComboBox.getSelectedItem(), diasRenta, tarifa);
	                }

	            } else {
	            	JOptionPane.showMessageDialog(null, "No hay carros disponibles en la categoría seleccionada, disculpas.");
	            	dispose();
	            }
	            
	            } catch (ParseException e) {
	            	JOptionPane.showMessageDialog(null, "Formato de la Fecha invalido");
	            	dispose();
	            }
	        }
	        }
	
	public void opcion2Cliente() {
		final JComboBox<String> listaDesplegable;
		final JTextField textField1;
		final JTextField textField2;
		
		setTitle("Escoger sede y rango de fechas");
        setSize(500, 175);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        getContentPane().removeAll();
		
		JPanel panel = new JPanel(new GridLayout(4, 2));
	
	    // Lista desplegable
	    String[] opciones = {"Sede", "Sucursal Norte", "Sucursal Central", "Sucursal Sur"};
	    listaDesplegable = new JComboBox<>(opciones);
	    panel.add(new JLabel("Selecciona una opción:"));
	    panel.add(listaDesplegable);
	
	    // Etiqueta y campo de texto 1
	    panel.add(new JLabel("Fecha Inicio:"));
	    textField1 = new JTextField();
	    panel.add(textField1);
	
	    // Etiqueta y campo de texto 2
	    panel.add(new JLabel("Fecha Final:"));
	    textField2 = new JTextField();
	    panel.add(textField2);
	
	    // Botón Confirmar
	    JButton confirmarButton = new JButton("Confirmar");
	    confirmarButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Lógica para manejar el botón de confirmar
	            String sedeOpcion = (String) listaDesplegable.getSelectedItem();
	            String fechaInicioStr = textField1.getText();
	            String fechaFinalStr = textField2.getText();
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	            try {
	                // Parsear la cadena a un objeto Date
	                Date fechaInicio = dateFormat.parse(fechaInicioStr);
	                Date fechaFinal = dateFormat.parse(fechaFinalStr);
	                if(sedeOpcion.equals("Sede")) {
		     			JOptionPane.showMessageDialog(null, "Elija una opción válida");
		     		}else {
		     			Sede sede = rentalSystem.getSedes().get(sedeOpcion);
		     			mostrarDisponibilidad(fechaInicio, fechaFinal, sede);
		     		}
	                
	            } catch (ParseException i) {
	            	JOptionPane.showMessageDialog(null, "La fecha no está en formato (dd/MM/yyyy)", "Formato Incorrecto", JOptionPane.ERROR_MESSAGE);
	            }
	            
	            
	        }
	    });
	    panel.add(confirmarButton);
	
	    // Botón Salir
	    JButton salirButton = new JButton("Salir");
	    salirButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Lógica para manejar el botón de salir
	            System.exit(0);
	        }
	    });
	    panel.add(salirButton);
	    add(panel);

        // Validar y refrescar
        revalidate();
        repaint();
        
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void mostrarDisponibilidad(Date fechaInicio, Date fechaFinal, Sede sede) {
		
		setTitle("Ver Disponibilidad");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        getContentPane().removeAll();
		JPanel panel = new JPanel();
		JLabel carros = new JLabel("Carros Disponibles para el rago de fechas");
		panel.add(carros);
		
		for (Vehiculo carro : sede.getVehiculos()) {
			if(carro.ValidarDisponibilidad(fechaInicio, fechaFinal)) {
				String infoCarro = carro.getVehiculoId()+ ": " + carro.getCategoria() + "-".repeat(4) + carro.getmarca() + "-".repeat(4) + carro.getmodelo();
				JLabel carrosDisponibles = new JLabel(infoCarro);
				panel.add(carrosDisponibles);
			}  	
		}
		add(panel);

        // Validar y refrescar
        revalidate();
        repaint();
        
		setLocationRelativeTo(null);
		setVisible(true);
	}

}

