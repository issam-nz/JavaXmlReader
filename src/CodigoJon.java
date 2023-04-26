import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CodigoJon {

    public static void main(String[] args) throws Throwable {
        // TODO Auto-generated method stub
        File fXmlFile = new File("prueba.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(fXmlFile);
        Element nodoRaiz = document.getDocumentElement();
        NodeList alumnos = nodoRaiz.getChildNodes();
        ArrayList<String> listaNombresAlumnos = devuelveAtributoAlumnos(alumnos,"nombre");
        ArrayList<String> listaMediasAlumnos = devuelveAtributoAlumnos(alumnos,"media");
        System.out.println("--------------------");
        System.out.println("PRIMER EJERCICIO:");
        System.out.println("--------------------");
        leerMediasAlumnos(listaNombresAlumnos, listaMediasAlumnos);
        System.out.println(" ");
        System.out.println("--------------------");
        System.out.println("SEGUNDO EJERCICIO");
        System.out.println("--------------------");
        devuelveNotasAlumnos(alumnos);


    }
    private static ArrayList<String> devuelveAtributoAlumnos(NodeList alumnos, String atributo){
        ArrayList<String> listaAtributo = new ArrayList<String>();
        for (int i=0; i<alumnos.getLength(); i++) {
            Node alumnoActual = alumnos.item(i);
            //System.out.println("El alumno (nodo) actual es: "+alumnoActual.getNodeName());
            //System.out.println(alumnoActual.getNodeName());
            if (alumnoActual.getNodeType() == Node.ELEMENT_NODE) {
                Element elementAlumnoActual = (Element) alumnoActual;
                String nombre = elementAlumnoActual.getAttribute(atributo);

                listaAtributo.add(nombre);

            }
        }
        return listaAtributo;
    }
    private static void devuelveNotasAlumnos(NodeList alumnos) {
        for (int i=0; i<alumnos.getLength(); i++) {
            Node alumnoActual = alumnos.item(i);
            if (alumnoActual.getNodeType() == Node.ELEMENT_NODE) {
                Element elementAlumnoActual = (Element) alumnoActual;
                String nombre = elementAlumnoActual.getAttribute("nombre");
                System.out.println(" ");
                System.out.println("ALUMNO: "+nombre);
                NodeList nodosAlumno = alumnoActual.getChildNodes();
                Node asignaturas =nodosAlumno.item(5);
                NodeList listaAsignaturas = asignaturas.getChildNodes();
                for (int x=0;x<listaAsignaturas.getLength();x++) {
                    Node asigActual = listaAsignaturas.item(x);

                    if (asigActual.getNodeType()==Node.ELEMENT_NODE) {
                        //System.out.println("la etiqueta es "+asigActual.getNodeName());
                        NodeList nodosasigActual = asigActual.getChildNodes();
                        //System.out.println("los nodos de asignatura son "+nodosasigActual.getLength());
                        for (int j=0;j<nodosasigActual.getLength();j++) {
                            Node nodoActual =nodosasigActual.item(j);
                            if(nodoActual.getNodeType()==Node.ELEMENT_NODE) {
                                //System.out.println("el nodo actual es "+nodoActual.getNodeName());
                                Element nodoAsignatura = (Element) nodoActual;
                                //System.out.println("El titulo "+nodoAsignatura.getTextContent());
                                if (nodoActual.getNodeName()=="titulo") {

                                    System.out.println("Asignatura: "+nodoAsignatura.getTextContent());
                                }else {
                                    System.out.println("Nota: "+nodoAsignatura.getTextContent());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static void devuelveAsignaturas(NodeList asignaturas) {
        System.out.println(asignaturas.getLength());
        for (int i=0; i<asignaturas.getLength(); i++) {
            Node asignaturaActual = asignaturas.item(i);
            Node titulo = asignaturaActual.getFirstChild();
            Node nota = asignaturaActual.getLastChild();
            Element elementTituloActual = (Element) titulo;
            Element elementNotaActual = (Element) nota;
            System.out.println("Asignatura: "+titulo);
            System.out.println("Nota: "+nota);
        }
    }

    private static void leerMediasAlumnos(ArrayList<String> listaAlumnos, ArrayList<String> listaMedias) {
        System.out.println("");
        System.out.println("LISTADO DE MEDIAS DE LOS ALUMNOS:");
        System.out.println("");
        for (int i = 0; i<listaAlumnos.size();i++) {
            System.out.println("El alumno "+listaAlumnos.get(i)+" "+"tiene una media de "+listaMedias.get(i));
        }
    }

}