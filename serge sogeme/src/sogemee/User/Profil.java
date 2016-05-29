/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sogemee.User;

/**
 *
 * @author el-diablo
 */
public class Profil {

    private int idProfil;
    private String libelleProfil;

    public Profil(int idProfil, String libelleProfil) {
        this.idProfil = idProfil;
        this.libelleProfil = libelleProfil;
    }

    public Profil(String libelleProfil) {
        this.libelleProfil = libelleProfil;
    }

    public int getIdProfil() {
        return idProfil;
    }

    public String getLibelleProfil() {
        return libelleProfil;
    }

    public void setIdProfil(int idProfil) {
        this.idProfil = idProfil;
    }

    public void setLibelleProfil(String libelleProfil) {
        this.libelleProfil = libelleProfil;
    }
    
    
    
}
