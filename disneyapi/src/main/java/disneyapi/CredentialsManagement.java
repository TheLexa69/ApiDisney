package disneyapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.jdom2.*;

//import java.io.File;
//import java.io.FileOutputStream;
import java.security.MessageDigest;
//import java.util.Scanner;

// import javax.xml.parsers.DocumentBuilder;
// import javax.xml.parsers.DocumentBuilderFactory;
// import javax.xml.parsers.ParserConfigurationException;
// import javax.xml.transform.OutputKeys;
// import javax.xml.transform.Transformer;
// import javax.xml.transform.TransformerFactory;
// import javax.xml.transform.dom.DOMSource;
// import javax.xml.transform.stream.StreamResult;
// import org.w3c.dom.Document;
// import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;

public class CredentialsManagement {
    // Función para hashear contraseñas usando SHA-256
    private static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Función para generar el archivo credentials.xml con los usuarios y contraseñas encriptadas
    public void generateCredentials() {
        String filePath = "disneyapi/src/main/resources/data/credentials.xml";
        
        try {
            //---- Crear el documento XML
            // DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            // DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            // Document doc = docBuilder.newDocument();
            
            //---- Nodo raíz <groupUsers>
            // Element rootElement = doc.createElement("groupUsers");
            // doc.appendChild(rootElement);
            
            //---- Crear el primer usuario
            // Element user1 = doc.createElement("user");
            // rootElement.appendChild(user1);
            // Element username1 = doc.createElement("username");
            // username1.appendChild(doc.createTextNode("profe"));
            // user1.appendChild(username1);
            // Element password1 = doc.createElement("password");
            // password1.appendChild(doc.createTextNode(hashPassword("profe")));
            // user1.appendChild(password1);
            // Element rol1 = doc.createElement("rol");
            // rol1.appendChild(doc.createTextNode("administrador"));
            // user1.appendChild(rol1);
            
            //---- Crear el segundo usuario
            // Element user2 = doc.createElement("user");
            // rootElement.appendChild(user2);
            // Element username2 = doc.createElement("username");
            // username2.appendChild(doc.createTextNode("estudiante"));
            // user2.appendChild(username2);
            // Element password2 = doc.createElement("password");
            // password2.appendChild(doc.createTextNode(hashPassword("estudiante")));
            // user2.appendChild(password2);
            // Element rol2 = doc.createElement("rol");
            // rol2.appendChild(doc.createTextNode("usuario"));
            // user2.appendChild(rol2);
            
            //---- Guardar el archivo XML
            // TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // Transformer transformer = transformerFactory.newTransformer();
            // transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // DOMSource source = new DOMSource(doc);
            // StreamResult result = new StreamResult(new FileOutputStream(new File(filePath)));
            // transformer.transform(source, result);
            
            System.out.println("Archivo credentials.xml generado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Función para leer las credenciales del archivo XML y verificar el login
    public boolean readCredentials(String inputUsername, String inputPassword) {
        String filePath = "disneyapi/src/main/resources/data/credentials.xml";
        try {
            //---- Parsear el archivo XML
            // File xmlFile = new File(filePath);
            // DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            // Document doc = dBuilder.parse(xmlFile);
            // doc.getDocumentElement().normalize();
            
            //---- Obtener los usuarios
            //NodeList usersList = doc.getElementsByTagName("user");
            
            // Hashear la contraseña ingresada para compararla
            String hashedInputPassword = hashPassword(inputPassword);
            
            //---- Recorrer la lista de usuarios
            // for (int i = 0; i < usersList.getLength(); i++) {
            //     Node node = usersList.item(i);
                
            //     if (node.getNodeType() == Node.ELEMENT_NODE) {
            //         Element element = (Element) node;
            //         String username = element.getElementsByTagName("username").item(0).getTextContent();
            //         String password = element.getElementsByTagName("password").item(0).getTextContent();
            //         String rol = element.getElementsByTagName("rol").item(0).getTextContent();
                    
            //         // Verificar si el usuario y la contraseña coinciden
            //         if (username.equals(inputUsername) && password.equals(hashedInputPassword)) {
            //             System.out.println("Login exitoso. Usuario: " + username + ", Rol: " + rol);
            //             return true;
            //         }
            //     }
            // }
            
            System.out.println("Credenciales incorrectas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        // CredentialsManager manager = new CredentialsManager();
        
        //---- Generar el archivo XML
        // manager.generateCredentials();
        
        //----  Simular login
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Ingrese su nombre de usuario: ");
        // String username = scanner.nextLine();
        // System.out.print("Ingrese su contraseña: ");
        // String password = scanner.nextLine();
        
        // boolean isLoginSuccessful = manager.readCredentials(username, password);
        // if (isLoginSuccessful) {
        //     System.out.println("Autenticación correcta.");
        // } else {
        //     System.out.println("Error en la autenticación.");
        // }
        
        // scanner.close();
    }
}
