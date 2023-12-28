import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame {
    private JTextField display;

    private double firstOperand;
    private String operator;

    public CalculatorApp() {
        // Set up the frame
        setTitle("Calculator");
        setSize(400, 600); // Adjusted default size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Monospaced", Font.PLAIN, 36)); // Increased font size
        display.setHorizontalAlignment(JTextField.RIGHT);

        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 24)); // Adjusted font size
            numberButtons[i].addActionListener(new NumberButtonListener());
        }

        JButton addButton = createOperationButton("+");
        JButton subtractButton = createOperationButton("-");
        JButton multiplyButton = createOperationButton("\u00D7");
        JButton divideButton = createOperationButton("\u00F7");
        JButton equalsButton = new JButton("=");
        JButton clearButton = new JButton("C");

        // Set fonts for operation buttons
        Font operationFont = new Font("Arial", Font.PLAIN, 24);
        addButton.setFont(operationFont);
        subtractButton.setFont(operationFont);
        multiplyButton.setFont(operationFont);
        divideButton.setFont(operationFont);
        equalsButton.setFont(operationFont);
        clearButton.setFont(operationFont);

        equalsButton.addActionListener(new EqualsButtonListener());
        clearButton.addActionListener(new ClearButtonListener());

        // Set layout
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        // Add components to the panel
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);

        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subtractButton);

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(multiplyButton);

        buttonPanel.add(clearButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(equalsButton);
        buttonPanel.add(divideButton);

        // Add components to the frame
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createOperationButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(new OperationButtonListener());
        return button;
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String currentText = display.getText();
            display.setText(currentText + button.getText());
        }
    }

    private class OperationButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            firstOperand = Double.parseDouble(display.getText());
            operator = button.getText();
            display.setText("");
        }
    }

    private class EqualsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double secondOperand = Double.parseDouble(display.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "\u00D7":
                    result = firstOperand * secondOperand;
                    break;
                case "\u00F7":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            display.setText(formatResult(result));
        }

        private String formatResult(double result) {
            if (result == (int) result) {
                return String.valueOf((int) result);
            } else {
                return String.valueOf(result);
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            display.setText("");
            firstOperand = 0;
            operator = "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculatorApp calculator = new CalculatorApp();
            calculator.setVisible(true);
        });
    }
}
