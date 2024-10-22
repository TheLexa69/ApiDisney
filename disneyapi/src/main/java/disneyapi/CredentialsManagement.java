package disneyapi;

import java.io.FileOutputStream;

import java.io.File;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CredentialsManagement {

    /**
     * Hashea una contraseña dada utilizando el algoritmo SHA-256.
     * 
     * @param password la contrase a a hashear
     * @return la contrase a hasheada en formato hexadecimal
     * @throws Exception si hay un error al obtener la instancia del algoritmo
     *                   SHA-256
     */
    public static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * Genera un archivo XML de credenciales con información del usuario.
     * 
     * Esta función crea un documento XML con datos del usuario como nombre de
     * usuario,
     * contraseña y rol. Luego guarda el archivo XML que contiene la información del
     * usuario.
     * 
     * La estructura XML incluye un elemento raíz "groupUsers" con múltiples
     * elementos "user".
     * Cada elemento "user" contiene elementos secundarios para nombre de usuario,
     * contraseña
     * y rol. Los nombres de usuario son "profe" y "estudiante", y las contraseñas
     * están
     * hashadas utilizando la función hashPassword.
     * 
     * @throws Exception si hay un error durante el proceso de creación del XML o
     *                   guardado del archivo.
     */
    public void generateCredentials() {
        String filePath = "disneyapi/src/main/resources/data/credentials.xml";
        try {
            // ---- Creamos el documento XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // ---- Nodo raíz <groupUsers>
            Element rootElement = doc.createElement("groupUsers");
            doc.appendChild(rootElement);

            // ---- Creamos el primer usuario
            Element user1 = doc.createElement("user");
            rootElement.appendChild(user1);
            Element iduser1 = doc.createElement("id");
            iduser1.appendChild(doc.createTextNode("1"));
            user1.appendChild(iduser1);
            Element username1 = doc.createElement("username");
            username1.appendChild(doc.createTextNode("profe"));
            user1.appendChild(username1);
            Element password1 = doc.createElement("password");
            password1.appendChild(doc.createTextNode(hashPassword("profe")));
            user1.appendChild(password1);
            Element rol1 = doc.createElement("rol");
            rol1.appendChild(doc.createTextNode("administrador"));
            user1.appendChild(rol1);

            // ---- Creamos el segundo usuario
            Element user2 = doc.createElement("user");
            rootElement.appendChild(user2);
            Element iduser2 = doc.createElement("id");
            iduser2.appendChild(doc.createTextNode("2")); // Cambiado a "2"
            user2.appendChild(iduser2); // Corrige aquí también
            Element username2 = doc.createElement("username");
            username2.appendChild(doc.createTextNode("estudiante"));
            user2.appendChild(username2);
            Element password2 = doc.createElement("password");
            password2.appendChild(doc.createTextNode(hashPassword("estudiante")));
            user2.appendChild(password2);
            Element rol2 = doc.createElement("rol");
            rol2.appendChild(doc.createTextNode("usuario"));
            user2.appendChild(rol2);

            // ---- Guardamos el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File(filePath)));
            transformer.transform(source, result);

            System.out.println("Archivo credentials.xml generado con éxito.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Comprueba si las credenciales ingresadas coinciden con alguna de las
     * credenciales en el archivo XML. Si coincide, devuelve true y si no, false.
     *
     * @param inputUsername el usuario ingresado
     * @param inputPassword la contrase a ingresada
     * @return true si las credenciales son correctas, false en caso contrario
     */
    public boolean readCredentials(String inputUsername, String inputPassword) {
        String filePath = "disneyapi/src/main/resources/data/credentials.xml";
        try {
            // ---- Parseamos el archivo XML
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // ---- Obtenemos los usuarios
            NodeList usersList = doc.getElementsByTagName("user");

            // Hashear la contraseña ingresada para compararla
            String hashedInputPassword = hashPassword(inputPassword);

            // ---- Recorremos la lista de usuarios
            for (int i = 0; i < usersList.getLength(); i++) {
                Node node = usersList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    String rol = element.getElementsByTagName("rol").item(0).getTextContent();

                    if (username.equals(inputUsername) && password.equals(hashedInputPassword)) {
                        System.out.println("Login exitoso. Usuario: " + username + ", Rol: " + rol);
                        return true;
                    }
                }
            }

            System.out.println("Credenciales incorrectas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Comprueba si las credenciales ingresadas coinciden con alguna de las
     * credenciales en el archivo XML. Si coincide, devuelve el rol del usuario
     * y si no, null.
     *
     * @param inputUsername el usuario ingresado
     * @param inputPassword la contrase a ingresada
     * @return el rol del usuario o null si las credenciales no son correctas
     */
    public String getUserRole(String inputUsername, String inputPassword) {
        String filePath = "disneyapi/src/main/resources/data/credentials.xml";
        try {
            // ---- Parseamos el archivo XML
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // ---- Obtenemos los usuarios
            NodeList usersList = doc.getElementsByTagName("user");

            // Hashear la contraseña ingresada para compararla
            String hashedInputPassword = hashPassword(inputPassword);

            // ---- Recorremos la lista de usuarios
            for (int i = 0; i < usersList.getLength(); i++) {
                Node node = usersList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String username = element.getElementsByTagName("username").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    String rol = element.getElementsByTagName("rol").item(0).getTextContent();

                    if (username.equals(inputUsername) && password.equals(hashedInputPassword)) {
                        System.out.println("Login exitoso. Usuario: " + username + ", Rol: " + rol);
                        return rol;
                    }
                }
            }

            System.out.println("Credenciales incorrectas.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
