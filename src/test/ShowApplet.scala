package test;

import javax.swing._
import java.awt._
import java.awt.event._

class ShowApplet extends JApplet {
  // 'val' or 'var' both ok; 'val' is ok because although
  // JTextArea changes, the reference to it, 'text' itself, does not.
  val text = new JTextArea()
  // 'int' or 'Int' both ok here, but 'Int' is more idiomatic
  var startCount: Int = 0

  override def init() {
    var button = new JButton("Press here!")
    button.addActionListener(new ActionListener() {
        def actionPerformed(e: ActionEvent) {
          text.append("Button Pressed!\n")
        }
      } )

    getContentPane().add("Center", new JScrollPane(text));
    var panel = new JPanel()
    panel.add(button)
    getContentPane().add("South", panel)
    text.append("Java Version: " +
                System.getProperty("java.version")+"\n")
    text.append("SCALA Applet Init()\n")
  }
  override def start() {
    text.append("Applet started: " + startCount + "\n")
    startCount = startCount + 1
  }
  override def stop() {
    text.append("Applet stopped.\n")
  }
}
