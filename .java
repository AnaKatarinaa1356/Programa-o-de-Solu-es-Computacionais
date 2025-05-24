// Usuário.java
public class Usuario {
    private String nome;
    private String email;
    private String cidade;

    public Usuario(String nome, String email, String cidade) {
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Email: " + email + ", Cidade: " + cidade;
    }
}

//Eevento
import java.time.LocalDateTime;

public class Evento {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        return "Evento: " + nome + ", Endereço: " + endereco + ", Categoria: " + categoria +
               ", Horário: " + horario + ", Descrição: " + descricao;
    }
}

//SistemaEventos
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class SistemaEventos {
    private List<Evento> listaEventos = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();

    public void cadastrarUsuario(String nome, String email, String cidade) {
        Usuario u = new Usuario(nome, email, cidade);
        listaUsuarios.add(u);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public void cadastrarEvento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        Evento e = new Evento(nome, endereco, categoria, horario, descricao);
        listaEventos.add(e);
        System.out.println("Evento cadastrado com sucesso!");
    }

    public void consultarEventos() {
        if(listaEventos.isEmpty()){
            System.out.println("Nenhum evento cadastrado.");
        } else {
            listaEventos.sort(Comparator.comparing(Evento::getHorario));
            for (Evento e : listaEventos) {
                System.out.println(e);
            }
        }
    }

    public void salvarEventos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("events.data"))) {
            for (Evento e : listaEventos) {
                pw.println(e.toString());
            }
        } catch (IOException ex) {
            System.out.println("Erro ao salvar eventos: " + ex.getMessage());
        }
    }

    public void carregarEventos() {
        try (BufferedReader br = new BufferedReader(new FileReader("events.data"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha); // ou pode implementar parser para recriar objetos
            }
        } catch (IOException ex) {
            System.out.println("Nenhum evento salvo previamente.");
        }
    }
}

//Main
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SistemaEventos sistema = new SistemaEventos();
        Scanner sc = new Scanner(System.in);
        sistema.carregarEventos();

        while (true) {
            System.out.println("1 - Cadastrar Usuário");
            System.out.println("2 - Cadastrar Evento");
            System.out.println("3 - Consultar Eventos");
            System.out.println("4 - Salvar e Sair");
            int opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            if (opcao == 1) {
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Cidade: ");
                String cidade = sc.nextLine();
                sistema.cadastrarUsuario(nome, email, cidade);
            } else if (opcao == 2) {
                System.out.print("Nome do evento: ");
                String nome = sc.nextLine();
                System.out.print("Endereço: ");
                String endereco = sc.nextLine();
                System.out.print("Categoria: ");
                String categoria = sc.nextLine();
                System.out.print("Descrição: ");
                String descricao = sc.nextLine();
                System.out.print("Horário (ano-mes-diaThoras:minutos): ");
                String horarioStr = sc.nextLine();
                LocalDateTime horario = LocalDateTime.parse(horarioStr);

                sistema.cadastrarEvento(nome, endereco, categoria, horario, descricao);
            } else if (opcao == 3) {
                sistema.consultarEventos();
            } else if (opcao == 4) {
                sistema.salvarEventos();
                break;
            }
        }

        sc.close();
    }
}
