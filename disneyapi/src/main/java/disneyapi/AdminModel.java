package disneyapi;

import java.io.FileOutputStream;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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

            //-- -- --System.out.println("Usuario añadido con éxito. " + username + " con rol: " + rol + "contraseña " + password);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Indica que hubo un error
        }
    }
}
