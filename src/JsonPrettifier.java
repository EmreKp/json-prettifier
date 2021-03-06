import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonPrettifier {
	public static void main(String[] args) {
		//init frame
		JFrame frame = new JFrame("JSON Prettifier");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(700, 400)); //can be adjusted later

		//set menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("About");
		menu.addMenuListener(new MenuListener() {
			@Override
			public void menuSelected(MenuEvent e) {
				JOptionPane.showMessageDialog(frame, "Emrekp 16.10.18");
			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}
		});
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);

		//create panel
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));
		pane.setLayout(new BorderLayout());

		//set textarea
		JTextArea textArea = new JTextArea("Bu da deneme tekstim");
		textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

		//set button
		JButton prettifyButton = new JButton("Prettify");
		prettifyButton.addActionListener(e -> {
			ObjectMapper mapper = new ObjectMapper();

			//set indenter because of default indenter has 2 spaces
			DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
			DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
			printer.indentObjectsWith(indenter);
			printer.indentArraysWith(indenter);

			Object pojo = null;
			try {
				pojo = mapper.readValue(textArea.getText(), Object.class);
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}

			String json = "";
			try {
				json = mapper.writer(printer).writeValueAsString(pojo);
			}
			catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}

			textArea.setText(json);
		});

		//add components
		pane.add(textArea, BorderLayout.CENTER);
		pane.add(prettifyButton, BorderLayout.PAGE_END);

		//show the frame
		frame.setContentPane(pane);
		frame.pack();
		frame.setVisible(true);
	}
}
