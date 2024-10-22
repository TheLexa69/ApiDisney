package disneyapi;

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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {

    /**
     * Añade un nuevo usuario al archivo XML de credenciales.
     * 
     * @param username nombre del usuario
     * @param password contrase a del usuario
     * @param rol      rol del usuario (puedes modificar esto seg n tus necesidades)
     * @return true si se logró agregar el usuario, false en caso de error
     * @throws Exception si hay un error al leer o escribir el archivo XML
     */
    public boolean addUser(String username, String password, String rol) throws Exception {
        try {
            String filePath = "disneyapi/src/main/resources/data/credentials.xml";

            // Cargamos el documento XML existente
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(filePath));

            // Obtenemos el nodo raíz <groupUsers>
            Element rootElement = doc.getDocumentElement();

            // Generamos un nuevo ID (puedes mejorar esto para obtener el ID más alto y
            // sumarle 1)
            int newId = rootElement.getElementsByTagName("user").getLength() + 1;

            // Creamos nuevo elemento <user>
            Element newUser = doc.createElement("user");

            // Creamos y agregamos el ID
            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(String.valueOf(newId)));
            newUser.appendChild(idElement);

            // Creamos y agregamos el nombre de usuario
            Element usernameElement = doc.createElement("username");
            usernameElement.appendChild(doc.createTextNode(username));
            newUser.appendChild(usernameElement);

            // Creamos y agregamos la contraseña hasheada
            Element passwordElement = doc.createElement("password");
            passwordElement.appendChild(doc.createTextNode(CredentialsManagement.hashPassword(password)));
            newUser.appendChild(passwordElement);

            // Creamos y agregamos el rol (puedes modificar esto según tus necesidades)
            Element rolElement = doc.createElement("rol");
            rolElement.appendChild(doc.createTextNode(rol)); // Cambia si es necesario
            newUser.appendChild(rolElement);

            // Añadimos el nuevo usuario al nodo raíz
            rootElement.appendChild(newUser);

            // Guardamos los cambios en el archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(new File(filePath)));
            transformer.transform(source, result);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un usuario del archivo de credenciales.
     * 
     * @param username el nombre de usuario a eliminar
     */
    public static void deleteUser(String username) {
        File file = new File("disneyapi/src/main/resources/data/credentials.xml");
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList userList = doc.getElementsByTagName("user");
            for (int i = 0; i < userList.getLength(); i++) {
                Node userNode = userList.item(i);
                Element userElement = (Element) userNode;

                String usernameInXml = userElement.getElementsByTagName("username").item(0).getTextContent();
                if (usernameInXml.equals(username)) {
                    userNode.getParentNode().removeChild(userNode);
                    break;
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Elimina un usuario del archivo de credenciales por su ID.
     * 
     * @param userId el ID del usuario a eliminar
     * @throws Exception si ocurre un error al procesar el archivo XML
     */
    public static void removeUserById(String userId) throws Exception {
        File file = new File("disneyapi/src/main/resources/data/credentials.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList userList = doc.getElementsByTagName("user");
        boolean userFound = false;

        for (int i = 0; i < userList.getLength(); i++) {
            Node userNode = userList.item(i);
            Element userElement = (Element) userNode;

            String idInXml = userElement.getElementsByTagName("id").item(0).getTextContent();
            System.out.println("Verificando ID: " + idInXml);

            if (idInXml.equals(userId)) {
                userNode.getParentNode().removeChild(userNode);
                userFound = true;
                System.out.println("Usuario con ID " + userId + " eliminado.");
                break;
            }
        }

        if (!userFound) {
            System.out.println("No se encontró el usuario con ID " + userId + "."); // Mensaje de no encontrado
        }

        // Guardamos los cambios de vuelta en el archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
    }

    /**
     * Recibe una lista de usuarios y los almacena en un archivo XML.
     * 
     * @return una lista de usuarios almacenados en un archivo XML
     * @throws Exception si ocurre un error al procesar el archivo XML lanza una
     *                   excepción
     */
    public static List<User> listUsers() throws Exception {
        List<User> users = new ArrayList<>();
        try {
            String filePath = "disneyapi/src/main/resources/data/credentials.xml";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(filePath));
            doc.getDocumentElement().normalize();

            for (int i = 0; i < doc.getElementsByTagName("user").getLength(); i++) {
                String id = doc.getElementsByTagName("id").item(i).getTextContent();
                String username = doc.getElementsByTagName("username").item(i).getTextContent();
                String role = doc.getElementsByTagName("rol").item(i).getTextContent();

                users.add(new User(id, username, role));
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
