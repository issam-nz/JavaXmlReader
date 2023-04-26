import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
    static ArrayList<Asignatura> asignaturas;

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File fxmlFile = new File("C:\\Users\\sakis\\eclipse-workspace\\JavaXml\\src\\alumnos.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fxmlFile);
        Element nodoRaiz = document.getDocumentElement();

        //arraylist para guardar todos los objetos de la clase Alumno -- cada uno con su id, nota, am y la lista de su asignaturas
        ArrayList<Alumno> alumnos = getAlumnos(nodoRaiz);

        mostrarAlumnos(alumnos);

        System.out.println("******************************************");

        //obten el numero de los alumnos que tienen una nota mayor que 7
        System.out.println("la cantidad de los alumnos que tienen una media mayor que 7 es: "+cantidadAlumnosMasQue(alumnos, 7));

        System.out.println("******************************************");
        System.out.println("las asignaturas suspendidas");
        //por cada alumno obten las asignaturas suspendidas (nombre asignatura y nota)
        asignaturasSuspendidas(alumnos);
        System.out.println("******************************************");
    }

    public static ArrayList<Asignatura> getAsignaturas(Element alumnoActualElement) {
        /*devuelve una lista de asignaturas */
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        //primero crear un nodo para el <asignaturas>
        // sin .item(0), el metodo .getElementsByTagName() devuelve un NodeList y no es lo que queremos porque sabemos que hay solamente un elemnto <asignaturas> en cada alumno
        Node asignaturasNode = alumnoActualElement.getElementsByTagName("asignaturas").item(0);

        //sacar la lista de nodos (hijos) del nodo <asignaturas>
        NodeList asignaturasNodeList = asignaturasNode.getChildNodes();

        //bouclear a la lista de nodos
        for (int j = 0; j < asignaturasNodeList.getLength(); j++) {

            //inicializar cada elemnto en la lista como Nodo
            Node asignaturaNode = asignaturasNodeList.item(j);

            if (asignaturaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element asignaturaElement = (Element) asignaturaNode;

                String tituloAsignatura = asignaturaElement.getAttribute("titulo");
                float notaAsignatura = Float.parseFloat(asignaturaElement.getAttribute("nota"));
//                System.out.println(tituloAsignatura +" "+ notaAsignatura);
                Asignatura asignatura = new Asignatura(tituloAsignatura, notaAsignatura);

                //guardar la asignatura actual en la lista de asignaturas ArrayList<Asignatura>
                asignaturas.add(asignatura);
            }
        }
        return asignaturas;
    }

    public static ArrayList<Alumno> getAlumnos(Element nodoRaiz) {
        /*devuelve una lista de alumnos*/
        ArrayList<Alumno> alumnos = new ArrayList<>();
        NodeList listaAlumnos = nodoRaiz.getChildNodes();

        for (int i = 0; i < listaAlumnos.getLength(); i++) {
            Node alumnoActualNode = listaAlumnos.item(i);
            if (alumnoActualNode.getNodeType() == Node.ELEMENT_NODE) { // or node.getNodeType() == Node.ELEMENT_NODE
                Element alumnoActualElement = (Element) alumnoActualNode;
//                System.out.println(alumnoActualElement.getAttribute("id") + " type " + alumnoActualElement.getAttribute("id").getClass().getSimpleName());

                //guardar los datos del alumno
                int id = Integer.parseInt(alumnoActualElement.getAttribute("id"));
                int media = Integer.parseInt(alumnoActualElement.getAttribute("media"));
                int asignaturasMatriculadas = Integer.parseInt(alumnoActualElement.getElementsByTagName("asignaturasMatriculadas").item(0).getTextContent());

                //crear instancia para el alumno actual
                Alumno alumno = new Alumno(id, media, asignaturasMatriculadas);

                //guardar la lista de asignaturas para el alumno
                alumno.setAsignaturas(getAsignaturas(alumnoActualElement));
                //guardar cada alumno en la lista de alumnos
                alumnos.add(alumno);
            }
        }
        return alumnos;
    }

    public static void mostrarAlumnos(ArrayList<Alumno> alumnos) {
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno actual = alumnos.get(i);
            System.out.println("alumno: " + actual.id);
            System.out.println("\tmedia: " + actual.media);
            System.out.println("\tmedia: " + actual.asignaturasMatriculadas);
            System.out.println("\tasignaturas: ");
            for (int j = 0; j < actual.asignaturas.size(); j++) {
                Asignatura asActual = actual.asignaturas.get(j);
                System.out.println("\t\t" + asActual.titulo + ": " + asActual.nota);
            }
            System.out.println("-------------");
        }
    }

    public static int cantidadAlumnosMasQue(ArrayList<Alumno> alumnos, int media) {
        //devuelve la cantidad de los alumnos que tienen la media mas que el parametro media
        int cantidad = 0;
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno alumnoActual = alumnos.get(i);
            if (alumnoActual.media > media) cantidad++;
        }
        return cantidad;
    }

    public static void asignaturasSuspendidas(ArrayList<Alumno> alumnos) {
        //mostrar las asignaturas suspendidas de cada alumno
        String texto = "";
        for (int i = 0; i < alumnos.size(); i++) {
            Alumno alumnoActual = alumnos.get(i);

            boolean flag = false;
            boolean tieneSuspendidas = false;
            int x = 0;

            for (int j = 0; j < alumnoActual.asignaturas.size(); j++) {
                Asignatura asignaturaActual = alumnoActual.asignaturas.get(j);
                if (asignaturaActual.nota < 5 ) {
//                    flag = true;
                    if (x == 0) {
                        x++;
//                        tieneSuspendidas = true;
                        texto += "Alumno" + alumnoActual.id + ": \n";
                    }
                    texto += "\t" + asignaturaActual.titulo + ": " + asignaturaActual.nota + "\n";
                }

            }
        }
        System.out.println(texto);
    }

}
