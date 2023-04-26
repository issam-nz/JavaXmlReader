import java.util.ArrayList;

public class Alumno {
    int id, media, asignaturasMatriculadas;
    ArrayList<Asignatura> asignaturas;

    Alumno(int id, int media,int am) {
        this.id = id;
        this.media = media;
        this.asignaturasMatriculadas = am;
    }

    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
