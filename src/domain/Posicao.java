package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Posicao extends EntidadeBase {

    private float latitude;
    private float longitude;
    private float altitude;
    private float precisao;
    private LocalDateTime timestamp;
    private char tipoRegistro;
    private Astronauta astronauta;
    private Satelite satelite;

    // Construtor completo (com associacoes do UML)
    public Posicao(int id, float latitude, float longitude, float altitude,
                   float precisao, LocalDateTime timestamp, char tipoRegistro,
                   Astronauta astronauta, Satelite satelite) {
        super(id);
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.precisao = precisao;
        this.timestamp = timestamp;
        this.tipoRegistro = tipoRegistro;
        this.astronauta = astronauta;
        this.satelite = satelite;
    }

    // Construtor sem associacoes (posicao independente)
    public Posicao(int id, float latitude, float longitude, float altitude,
                   float precisao, LocalDateTime timestamp, char tipoRegistro) {
        this(id, latitude, longitude, altitude, precisao, timestamp, tipoRegistro, null, null);
    }

    public Posicao() {}

    @Override
    public String gerarRelatorio() {
        String ts = timestamp != null
                ? timestamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                : "N/A";
        System.out.println("==================================================");
        System.out.println("         RELATORIO DE POSICAO                     ");
        System.out.println("==================================================");
        System.out.println("ID: " + id + " | Tipo: " + tipoRegistro);
        System.out.println("Latitude: " + latitude + " | Longitude: " + longitude);
        System.out.println("Altitude: " + altitude + "m | Precisao: " + precisao + "m");
        System.out.println("Registrada em: " + ts);
        if (astronauta != null) System.out.println("Astronauta: " + astronauta.getNome());
        if (satelite != null)   System.out.println("Satelite: " + satelite.getNome());
        System.out.println("==================================================");
        return "";
    }

    public String getResumo() {
        return String.format("[%d] Lat: %.4f | Lon: %.4f | Alt: %.1fm | Tipo: %c",
                id, latitude, longitude, altitude, tipoRegistro);
    }

    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }

    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }

    public float getAltitude() { return altitude; }
    public void setAltitude(float altitude) { this.altitude = altitude; }

    public float getPrecisao() { return precisao; }
    public void setPrecisao(float precisao) { this.precisao = precisao; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public char getTipoRegistro() { return tipoRegistro; }
    public void setTipoRegistro(char tipoRegistro) { this.tipoRegistro = tipoRegistro; }

    public Astronauta getAstronauta() { return astronauta; }
    public void setAstronauta(Astronauta astronauta) { this.astronauta = astronauta; }

    public Satelite getSatelite() { return satelite; }
    public void setSatelite(Satelite satelite) { this.satelite = satelite; }
}
