
import java.io.Serializable;

public class Proprietario implements Serializable {
    private String idProprietario;
    private String propnome;
    private int contacto;

    public String getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(String idProprietario) {
        this.idProprietario = idProprietario;
    }

    public String getPropnome() {
        return propnome;
    }

    public void setPropnome(String propnome) {
        this.propnome = propnome;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    
    }
    }