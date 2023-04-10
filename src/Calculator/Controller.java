package Calculator;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Controller
{
  @FXML private Button button0;
  @FXML private Button button1;
  @FXML private Button button2;
  @FXML private Button button3;
  @FXML private Button button4;
  @FXML private Button button5;
  @FXML private Button button6;
  @FXML private Button button7;
  @FXML private Button button8;
  @FXML private Button button9;
  @FXML private Button buttonComma;
  @FXML private Button buttonEquals;
  @FXML private Button buttonAdd;
  @FXML private Button buttonMultiply;
  @FXML private Button buttonSubtract;
  @FXML private Button buttonDivide;
  @FXML private Button buttonRemoveOne;
  @FXML private Button buttonDelete;

  @FXML private TextField textField;
  @FXML private TextField tempTextField;




  private final String ILLEGAL_PREVIOUS_CHARACTER = ".,+/*-";

  public void insertDigitToTextField(String digit)
  {
    if(currentEvent != null)
    {
      if(currentEvent.getCode() == KeyCode.ENTER)
        return;
    }
    textField.setText(textField.getText() + digit);
    try
    {
      char c = tempTextField.getText().charAt(tempTextField.getText().length() - 1);
      if (c == ',')
        tempTextField.setText(tempTextField.getText() + digit);
      else if (ILLEGAL_PREVIOUS_CHARACTER.indexOf(c) == -1)
        tempTextField.setText(tempTextField.getText() + digit);
      else
        tempTextField.setText(digit);
    }
    catch (StringIndexOutOfBoundsException ignored)
    {
      tempTextField.setText(tempTextField.getText() + digit);
    }
  }

  public void removeOneCharacter()
  {
    try
    {
      StringBuilder str = new StringBuilder(textField.getText());
      str.deleteCharAt(str.length() - 1);
      textField.setText(str.toString());
      str = new StringBuilder(tempTextField.getText());
      str.deleteCharAt(str.length() - 1);
      tempTextField.setText(str.toString());
    }
    catch (Exception e)
    {

    }
  }

  public void comma()
  {
    try
    {
      for(int i = textField.getLength() - 1; i >= 0; i--)
      {
        char c = textField.getText().charAt(i);
        if(c == ',')
        {
          return;
        }
        else if(ILLEGAL_PREVIOUS_CHARACTER.indexOf(c) != -1) // contains
        {
          break;
        }
      }
      textField.setText(textField.getText() + ",");
      tempTextField.setText(tempTextField.getText() + ",");
    }
    catch (IndexOutOfBoundsException e)
    {}
  }

  public void multiply()
  {
    try
    {
      char c = textField.getText().charAt(textField.getLength() - 1);
      if(ILLEGAL_PREVIOUS_CHARACTER.indexOf(c) == -1)
      {
        textField.setText(textField.getText() + "*");
        tempTextField.setText("*");
      }
    }
    catch (IndexOutOfBoundsException e)
    {}
  }

  public void add()
  {
    try
    {
      char c = textField.getText().charAt(textField.getLength() - 1);
      if(ILLEGAL_PREVIOUS_CHARACTER.indexOf(c) == -1)
      {
        textField.setText(textField.getText() + "+");
        tempTextField.setText("+");
      }
    }
    catch (IndexOutOfBoundsException e)
    {}
  }

  public void subtract()
  {
    try
    {
      char c = textField.getText().charAt(textField.getLength() - 1);
      if(ILLEGAL_PREVIOUS_CHARACTER.indexOf(c) == -1)
      {
        textField.setText(textField.getText() + "-");
        tempTextField.setText("-");
      }
    }
    catch (IndexOutOfBoundsException e)
    {}
  }

  public void divide()
  {
    try
    {
      char c = textField.getText().charAt(textField.getLength() - 1);
      if(ILLEGAL_PREVIOUS_CHARACTER.indexOf(c) == -1)
      {
        textField.setText(textField.getText() + "/");
        tempTextField.setText("/");
      }
    }
    catch (IndexOutOfBoundsException e)
    {}
  }

  public void equal()
  {
    String line = textField.getText();
    String tempLine = line;

    if(line.isEmpty())
      return;

    try
    {
      if (line.contains("\u221E") || line.contains("-\u221E"))
      {
        textField.setText("");
        tempTextField.setText("ERROR");
        return;
      }
    }
    catch (Exception e)
    {}

    if(ILLEGAL_PREVIOUS_CHARACTER.indexOf(line.charAt(line.length() - 1)) != -1) // contains operator
    {
      return;
    }

    line = line.replaceAll(",", ".");
    ArrayList<Character> operations = new ArrayList<>();
    ArrayList<Double> numbers = new ArrayList<>();
    String currentNumber = "";
    for (int i = 0; i < line.length(); i++)
    {
      char c = line.charAt(i);
      if (c == '/' || c == '-' || c == '+' || c == '*')
      {
        if(i == 0 && c == '-')
        {
          currentNumber += "-";
        }
        else
        {
          numbers.add(Double.parseDouble(currentNumber));
          currentNumber = "";
          operations.add(c);
        }
      }
      else if (c != ' ')
      {
        currentNumber += c;
      }
    }
    String number = currentNumber.replace("=", "");
    numbers.add(Double.parseDouble(number));

    line = "";
    for(int i = 0; i < operations.size(); i++)
    {
      line += numbers.get(i);
      line += operations.get(i);
    }
    line += numbers.get(numbers.size() - 1);

    line = line.replaceAll(" ", "");
    line = line.replaceAll("=", "");

    double number1 = 0;
    double number2 = 0;
    double resNumber = 0;

    for (int i = 1; i < line.length(); i++)
    {
      char operation = ' ';
      char c = line.charAt(i);
      String temp = "";
      boolean isBig = false;
      if (c == '/' || c == '*' || c == 'E')
      {
        operation = c;
        for (int j = i - 1; true; j--)
        {

          temp += line.charAt(j);
          if (line.charAt(j) == '/' || line.charAt(j) == '*'
              || line.charAt(j) == '-' || line.charAt(j) == '+' || j == 0 || c == 'E')
          {
            if (c == 'E')
            {
              temp = "";
              j += 3;
              while(true)
              {
                if(line.charAt(j) == '/' || line.charAt(j) == '*'
                    || line.charAt(j) == '-' || line.charAt(j) == '+' || j == line.length() - 1)
                {
                  break;
                }
                j++;
              }
              if(j != line.length() - 1)
                j--;
              while(true)
              {
                if(line.charAt(j) == '-' || line.charAt(j) == '+')
                {
                  break;
                }
                temp += line.charAt(j);
                j--;
              }
              temp += "-";
              c = 'e';
            }
            else
            {
              StringBuilder reversedTemp = new StringBuilder(temp);
              if (j != 0)
                reversedTemp.deleteCharAt(temp.length() - 1);
              reversedTemp.reverse();
              number1 = Double.parseDouble(reversedTemp.toString());
              break;
            }
          }
        }
        temp = "";

        for (int j = i + 1; true; j++)
        {
          temp += line.charAt(j);
          if (line.charAt(j) == '/' || line.charAt(j) == '*'
              || line.charAt(j) == '-' || line.charAt(j) == '+' || (j == line.length() - 1) || c == 'e')
          {
            if(c == 'e')
            {
              temp = "";
              while(true)
              {
                j++;
                if(line.charAt(j) == '/' || line.charAt(j) == '*'
                    || line.charAt(j) == '-' || line.charAt(j) == '+' || (j == line.length() - 1))
                {
                  if (j == line.length() - 1)
                  {
                    j-=2;
                  }
                  c = line.charAt(j);
                  break;
                }
              }
              isBig = true;
              break;
            }
            else
            {
              StringBuilder str = new StringBuilder(temp);
              if (j != line.length() - 1)
                str.deleteCharAt(temp.length() - 1);
              number2 = Double.parseDouble(str.toString());
              i = -1;
              break;
            }
          }
        }

        if(isBig)
          break;

        switch (c)
        {
          case '/':
            resNumber = number1 / number2;
            break;
          case '*':
            resNumber = number1 * number2;
            break;
          default:
            break;
        }
//        operation = c;

        String str = String.valueOf(number1) + c + String.valueOf(number2);
        temp = line.replace(str, String.valueOf(resNumber));
        line = temp;
      }
    }

    boolean isNegative = line.charAt(0) == '-';

    for (int i = 1; i < line.length(); i++)
    {
      char operation = ' ';
      char c = line.charAt(i);
      String temp = "";
      boolean isBig = false;
      if (c == '-' || c == '+' || c == 'E')
      {
        operation = c;
        for (int j = i - 1; true; j--)
        {

          if(j == -1)
            break;
          temp += line.charAt(j);


          if (line.charAt(j) == '/' || line.charAt(j) == '*'
              || line.charAt(j) == '-' || line.charAt(j) == '+' || j == 0 || c == 'E')
          {
            if (c == 'E')
            {
              temp = "";
              j += 3;
              while(true)
              {
                if(line.charAt(j) == '/' || line.charAt(j) == '*'
                    || line.charAt(j) == '-' || line.charAt(j) == '+' || j == line.length() - 1)
                {
                  break;
                }
                j++;
              }
              if(j != line.length() - 1)
                j--;
              while(true)
              {
                if(line.charAt(j) == '-' || line.charAt(j) == '+')
                {
                  break;
                }
                temp += line.charAt(j);
                j--;
              }
              temp += "-";
              c = 'e';
            }
            else
            {
              StringBuilder reversedTemp = new StringBuilder(temp);
              if (j != 0)
                reversedTemp.deleteCharAt(temp.length() - 1);
              reversedTemp.reverse();
              number1 = Double.parseDouble(reversedTemp.toString()); // todo if big value, exception (minus)
            }
          }
        }

        isNegative = line.charAt(0) == '-';
        if(isNegative)
          break;

        temp = "";

        for (int j = i + 1; true; j++)
        {
          temp += line.charAt(j);
          if (line.charAt(j) == '/' || line.charAt(j) == '*'
              || line.charAt(j) == '-' || line.charAt(j) == '+' || (j == line.length() - 1) || c == 'e')
          {
            if(c == 'e')
            {
              temp = "";
              while(true)
              {
                j++;
                if (line.charAt(j) == '/' || line.charAt(j) == '*'
                    || line.charAt(j) == '-' || line.charAt(j) == '+' || (j
                    == line.length() - 1))
                {
                  if (j == line.length() - 1)
                  {
                    j -= 2;
                  }
                  c = line.charAt(j);
                  break;
                }
              }
                isBig = true;
                break;
            }
            else
            {
              StringBuilder str = new StringBuilder(temp);
              if (j != line.length() - 1)
                str.deleteCharAt(temp.length() - 1);
              number2 = Double.parseDouble(str.toString()); // todo minus value!!
              i = -1;
              break;
            }
          }
        }

        if(isBig)
          break;

        switch (c)
        {
          case '+':
            resNumber = number1 + number2;
            break;
          case '-':
            resNumber = number1 - number2;
            break;
          default:
            break;
        }


        String str = String.valueOf(number1) + operation + String.valueOf(number2);
        temp = line.replace(str, String.valueOf(resNumber));
        line = temp;
      }
    }
    DecimalFormat df = new DecimalFormat("#.############");
    if(String.valueOf(resNumber).indexOf('E') == -1)
    {
      textField.setText(String.valueOf(df.format(resNumber)));
      tempTextField.setText(String.valueOf(df.format(resNumber)));
    }
    else {
      textField.setText(String.valueOf(resNumber));
      tempTextField.setText(String.valueOf(resNumber));
      }
  }

  public void handleClickButton(ActionEvent e)
  {
    if (e.getSource() == button0)
    {
      insertDigitToTextField("0");
    }
    else if (e.getSource() == button1)
    {
      insertDigitToTextField("1");
    }
    else if (e.getSource() == button2)
    {
      insertDigitToTextField("2");
    }
    else if (e.getSource() == button3)
    {
      insertDigitToTextField("3");
    }
    else if (e.getSource() == button4)
    {
      insertDigitToTextField("4");
    }
    else if (e.getSource() == button5)
    {
      insertDigitToTextField("5");
    }
    else if (e.getSource() == button6)
    {
      insertDigitToTextField("6");
    }
    else if (e.getSource() == button7)
    {
      insertDigitToTextField("7");
    }
    else if (e.getSource() == button8)
    {
      insertDigitToTextField("8");
    }
    else if (e.getSource() == button9)
    {
      insertDigitToTextField("9");
    }
    else if (e.getSource() == buttonDelete)
    {
      textField.setText("");
      tempTextField.setText("");
    }
    else if(e.getSource() == buttonRemoveOne)
    {
      removeOneCharacter();
    }
    else if (e.getSource() == buttonComma)
    {
      comma();
    }
    else if (e.getSource() == buttonMultiply)
    {
      multiply();
    }
    else if (e.getSource() == buttonAdd)
    {
      add();
    }
    else if (e.getSource() == buttonSubtract)
    {
      subtract();
    }
    else if (e.getSource() == buttonDivide)
    {
      divide();
    }
    else if (e.getSource() == buttonEquals)
    {
      equal();
    }
  }

  private KeyEvent currentEvent;

  public void handleKeyEvent(KeyEvent e)
  {
    currentEvent = e;

    System.out.println(e.getCode());
    if (e.getCode() == KeyCode.DIGIT0)
    {
      insertDigitToTextField("0");
    }
    else if (e.getCode() == KeyCode.DIGIT1)
    {
      insertDigitToTextField("1");
    }
    else if (e.getCode() == KeyCode.DIGIT2)
    {
      insertDigitToTextField("2");
    }
    else if (e.getCode() == KeyCode.DIGIT3)
    {
      insertDigitToTextField("3");
    }
    else if (e.getCode() == KeyCode.DIGIT4)
    {
      insertDigitToTextField("4");
    }
    else if (e.getCode() == KeyCode.DIGIT5)
    {
      insertDigitToTextField("5");
    }
    else if (e.getCode() == KeyCode.DIGIT6)
    {
      insertDigitToTextField("6");
    }
    else if (e.getCode() == KeyCode.DIGIT7)
    {
      insertDigitToTextField("7");
    }
    else if (e.getCode() == KeyCode.DIGIT8 && !e.isShiftDown())
    {
      insertDigitToTextField("8");
    }
    else if (e.getCode() == KeyCode.DIGIT9)
    {
      insertDigitToTextField("9");
    }
    else if (e.getCode() == KeyCode.ESCAPE)
    {
      textField.setText("");
      tempTextField.setText("");
    }
    else if(e.getCode() == KeyCode.BACK_SPACE)
    {
      removeOneCharacter();
    }
    else if (e.getCode() == KeyCode.COMMA)
    {
      comma();
    }
    else if (e.isShiftDown() && e.getCode() == KeyCode.DIGIT8)
    {
      multiply();
    }
    else if (e.isShiftDown() && e.getCode() == KeyCode.EQUALS)
    {
      add();
    }
    else if (e.getCode() == KeyCode.MINUS)
    {
      subtract();
    }
    else if (e.getCode() == KeyCode.SLASH)
    {
      divide();
    }
    else if (e.getCode() == KeyCode.EQUALS || e.getCode() == KeyCode.ENTER)
    {
      equal();
    }
  }
}
