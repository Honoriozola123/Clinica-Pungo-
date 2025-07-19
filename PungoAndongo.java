import java.time.LocalDate;
import java.util.*;
import java.io.*;

public class PungoAndongo {
    // Nome do arquivo onde os dados serão salvos
    private static final String FILENAME = "animais.txt";
    // Listas que armazenam os dados dos animais, proprietários e visitas
    private static final ArrayList<Animal> novo = new ArrayList<>();
    private static final ArrayList<Proprietario> p = new ArrayList<>();
    private static final ArrayList<Visita> v = new ArrayList<>();
    // Scanner para entrada de dados
    private static final Scanner sc = new Scanner(System.in); // Scanner global
    // Gera um ID automático para o animal
    private static String gerarIDAnimal(int i) {
        return "cli" + String.format("%03d", i + 1) + "ao";
    }
    // Gera um ID automático para o proprietário
    private static String gerarIDProprietario(int i) {
        return "pro" + String.format("%03d", i + 1) + "ao";
    }
    // Valida uma string (apenas letras e espaços)
    private static String validarString() {
        String entrada = sc.nextLine();
        if (entrada.matches("[a-zA-Z\\s]+")) {
            return entrada.toUpperCase();
        }
        System.out.println("Entrada inválida. Tente novamente.");
        return validarString();
    }
    // Valida o contacto (apenas número)
    private static int validarContacto() {
        String entrada = sc.nextLine();
        if (entrada.matches("\\d{9}")) {
            return Integer.parseInt(entrada);
        }
        System.out.println("Contacto inválido. Digite 9 dígitos.");
        return validarContacto();
    }
    // Valida a visita
    private static String validarTipoVisita() {
        String entrada = sc.nextLine().toLowerCase();
        if (Arrays.asList("higiene", "consulta", "vacina").contains(entrada)) {
            return entrada.toUpperCase();
        }
        System.out.println("Tipo inválido. Escolha entre: higiene, consulta, vacina");
        return validarTipoVisita();
    }
     // Valida o custo
    private static float validarCusto() {
        String entrada = sc.nextLine();
        if (entrada.matches("\\d+")) {
            float custo = Float.parseFloat(entrada);
            if (custo > 0) return custo;
        }
        System.out.println("Custo inválido.");
        return validarCusto();
    }
    // Valida o ano
    private static int validarAno() {
        String entrada = sc.nextLine();
        if (entrada.matches("\\d{4}")) {
            int ano = Integer.parseInt(entrada);
            if (ano >= 2020 && ano <= 2025) return ano;
            else System.out.println("Ano fora do intervalo permitido.");
        } else {
            System.out.println("Ano inválido.");
        }
        return validarAno();
    }
    // simula um limpa tela
    public static void limpa() {
        for (int i = 0; i < 50; i++) System.out.println();
    }
    // procedimento para o animal
    private static void registrarAnimal() {
        Animal a = new Animal();

        System.out.print("Nome do animal: ");
        a.setNome(validarString());

        System.out.print("Espécie: ");
        a.setEspecie(validarString());

        System.out.print("Raça: ");
        a.setRaca(validarString());

        System.out.print("Ano de nascimento: ");
        int anoNasci = validarAno();
        a.setAnoNascimento(anoNasci);
        int idade = 2025 - anoNasci;
        a.setIdade(idade);

        if (idade > 5) {
            System.out.println("Animal com idade superior a 5 anos não permitido.");
            return;
        }

        a.setIdAnimal(gerarIDAnimal(novo.size()));
        System.out.println("ID DO ANIMAL: " + a.getIdAnimal());

        if (p.isEmpty()) {
            System.out.println("Nenhum proprietário registrado. Cadastre um proprietário primeiro.");
            return;
        }

        Proprietario ultimo = p.get(p.size() - 1);
        a.setProprietario(ultimo);

        novo.add(a);
        System.out.println("REGISTRO DO ANIMAL REALIZADO COM SUCESSO!");
    }
    //procedimento para o proprietário
    private static void registrarProprietario() {
        Proprietario prop = new Proprietario();

        System.out.print("Nome do proprietário: ");
        prop.setPropnome(validarString());

        System.out.print("Contacto: ");
        prop.setContacto(validarContacto());

        prop.setIdProprietario(gerarIDProprietario(p.size()));
        System.out.println("ID DO PROPRIETARIO: " + prop.getIdProprietario());

        p.add(prop);
        System.out.println("REGISTRO DO PROPRIETÁRIO REALIZADO COM SUCESSO!");
    }
    //procedimento para a visita
    private static void registrarVisita() {
    Visita visita = new Visita();

    if (novo.isEmpty()) {
        System.out.println("Nenhum animal cadastrado. Registre um animal primeiro.");
        return;
    }

    System.out.println("=== Animais disponíveis ===");
    for (Animal a : novo) {
        System.out.println("ID: " + a.getIdAnimal() + " | Nome: " + a.getNome());
    }

    System.out.print("Digite o ID do animal para associar à visita: ");
    String idAnimal = sc.nextLine().toUpperCase().trim();

    Animal animalAssociado = null;
    for (Animal animal : novo) {
        if (animal.getIdAnimal().trim().equalsIgnoreCase(idAnimal)) {
            animalAssociado = animal;
            break;
        }
    }

    if (animalAssociado == null) {
        System.out.println("ID do animal não encontrado. Visita não registrada.");
        return;
    }

    visita.setAnimal(animalAssociado);

    System.out.print("Tipo de visita (higiene, consulta ou vacina): ");
    visita.setTipo(validarTipoVisita());

    System.out.print("Observações: ");
    visita.setObservacoes(validarString());

    System.out.print("Custo: ");
    visita.setCusto(validarCusto());

    boolean entradaValida = false;
    while (!entradaValida) {
        System.out.print("Digite a data (AAAA-MM-DD): ");
        String dataStr = sc.nextLine();
        try {
            LocalDate data = LocalDate.parse(dataStr);
            if (data.isBefore(LocalDate.of(2024, 1, 1)) || data.isAfter(LocalDate.of(2025, 12, 30))) {
                System.out.println("Data fora do intervalo permitido.");
            } else {
                visita.setData(dataStr);
                entradaValida = true;
            }
        } catch (Exception e) {
            System.out.println("Formato de data inválido.");
        }
    }

    System.out.print("Nome do veterinário: ");
    visita.setVet(validarString());

    v.add(visita);
    System.out.println("VISITA REGISTRADA COM SUCESSO!");
}

//procedimento para a listgem
    private static void listarAnimais() {
        if (novo.isEmpty()) {
            System.out.println("Nenhum animal registrado ainda.");
            return;
        }

        for (Animal a : novo) {
            System.out.println("\n-------------------------------");
            System.out.println("ID ANIMAL: " + a.getIdAnimal());
            System.out.println("NOME: " + a.getNome());
            System.out.println("ESPÉCIE: " + a.getEspecie());
            System.out.println("RAÇA: " + a.getRaca());
            System.out.println("IDADE: " + a.getIdade() + " anos");

            Proprietario prop = a.getProprietario();
            if (prop != null) {
                System.out.println("PROPRIETÁRIO: " + prop.getPropnome());
                System.out.println("ID PROPRIETÁRIO: " + prop.getIdProprietario());
                System.out.println("CONTACTO: " + prop.getContacto());
            }

            boolean temVisita = false;
            for (Visita vis : v) {
                if (vis.getAnimal() != null && vis.getAnimal().getIdAnimal().equals(a.getIdAnimal())) {
                    temVisita = true;
                    System.out.println("VISITA: " + vis.getData() + " | TIPO: " + vis.getTipo());
                    System.out.println("CUSTO: " + vis.getCusto() + " KZ");
                    System.out.println("VETERINÁRIO: " + vis.getVet());
                    System.out.println("OBSERVAÇÕES: " + vis.getObservacoes());
                }
            }

            if (!temVisita) {
                System.out.println("SEM VISITAS REGISTRADAS");
            }
        }
    }
// procedimento para o histórico do animal
    private static void historicoVisitasPorAnimal() {
    Scanner sc = new Scanner(System.in);

    if (novo.isEmpty()) {
        System.out.println("Nenhum animal registrado.");
        return;
    }

    System.out.println("=== Animais registrados ===");
    for (Animal a : novo) {
        System.out.println("ID: " + a.getIdAnimal() + " | Nome: " + a.getNome());
    }

    System.out.print("Digite o ID do animal: ");
    String id = sc.nextLine().trim().toUpperCase();

    Animal encontrado = null;
    for (Animal a : novo) {
        if (a.getIdAnimal().trim().equalsIgnoreCase(id)) {
            encontrado = a;
            break;
        }
    }

    if (encontrado == null) {
        System.out.println("Animal não encontrado.");
        return;
    }

    int total = 0, h = 0, c = 0, vac = 0;
    for (Visita vis : v) {
        if (vis.getAnimal() != null && vis.getAnimal().getIdAnimal().equalsIgnoreCase(encontrado.getIdAnimal())) {
            total++;
            switch (vis.getTipo().toLowerCase()) {
                case "higiene": h++; break;
                case "consulta": c++; break;
                case "vacina": vac++; break;
            }
        }
    }

    System.out.println("\n===== HISTÓRICO DE VISITAS =====");
    System.out.println("ID: " + encontrado.getIdAnimal() + " | Nome: " + encontrado.getNome());
    System.out.println("Total de visitas: " + total);
    System.out.println("Higiene: " + h);
    System.out.println("Consulta: " + c);
    System.out.println("Vacina: " + vac);
}

//procedimento para consulta
   private static void consultaPorID() {
    Scanner sc = new Scanner(System.in);

    if (novo.isEmpty()) {
        System.out.println("Nenhum animal registrado.");
        return;
    }

    System.out.println("=== Animais Registrados ===");
    for (Animal a : novo) {
        System.out.println("ID: " + a.getIdAnimal() + " | Nome: " + a.getNome());
    }

    System.out.print("\nDigite o ID do animal: ");
    String id = sc.nextLine().trim().toUpperCase();

    Animal encontrado = null;
    for (Animal a : novo) {
        if (a.getIdAnimal().trim().equalsIgnoreCase(id)) {
            encontrado = a;
            break;
        }
    }

    if (encontrado == null) {
        System.out.println("Animal não encontrado.");
        return;
    }

    listarAnimalDetalhado(encontrado);
}

   //procedimento para listar animais
    private static void listarAnimalDetalhado(Animal a) {
        System.out.println("\n=== Animal ID: " + a.getIdAnimal());
        System.out.println("Nome: " + a.getNome());
        System.out.println("Espécie: " + a.getEspecie());
        System.out.println("Raça: " + a.getRaca());
        System.out.println("Idade: " + a.getIdade());
        if (a.getProprietario() != null) {
            System.out.println("Proprietário: " + a.getProprietario().getPropnome());
            System.out.println("Contacto: " + a.getProprietario().getContacto());
        }

        for (Visita visita : v) {
            if (visita.getAnimal() != null && visita.getAnimal().getIdAnimal().equals(a.getIdAnimal())) {
                System.out.println("\n  - Visita em " + visita.getData());
                System.out.println("    Tipo: " + visita.getTipo());
                System.out.println("    Veterinário: " + visita.getVet());
                System.out.println("    Observações: " + visita.getObservacoes());
                System.out.println("    Custo: " + visita.getCusto() + " KZ");
            }
        }
    }
    //procedimento para salvar os dados
    private static void salvarDadosEmArquivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            out.writeObject(novo);
            out.writeObject(p);
            out.writeObject(v);
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private static void carregarDadosDoArquivo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILENAME))) {
            novo.clear();
            p.clear();
            v.clear();

            novo.addAll((ArrayList<Animal>) in.readObject());
            p.addAll((ArrayList<Proprietario>) in.readObject());
            v.addAll((ArrayList<Visita>) in.readObject());

            v.removeIf(vis -> vis.getAnimal() == null);
            System.out.println("Dados carregados com sucesso do arquivo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum dado anterior encontrado ou erro ao carregar: " + e.getMessage());
        }
    }
    //metódo principal
    public static void main(String[] args) {
        int opcao;
        carregarDadosDoArquivo();
        do {
            System.out.println("\n=== MENU CLÍNICA VETERINÁRIA ===");
            System.out.println("1. REGISTAR PROPRIETÁRIO");
            System.out.println("2. REGISTAR ANIMAL");
            System.out.println("3. REGISTAR VISITA");
            System.out.println("4. HISTÓRICO DE VISITAS POR ANIMAL");
            System.out.println("5. LISTAR ANIMAIS");
            System.out.println("6. CONSULTA POR ID");
            System.out.println("7. GUARDAR OS DADOS EM ARQUIVOS ");
            System.out.println("0. SAIR");
            System.out.print("OPÇÃO: ");

            while (!sc.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número:");
                sc.next();
            }

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1: limpa(); 
                registrarProprietario(); break;
                case 2: limpa(); 
                registrarAnimal(); break;
                case 3: limpa(); 
                registrarVisita(); break;
                case 4: limpa(); 
                historicoVisitasPorAnimal(); break;
                case 5:
                listarAnimais(); limpa();  break;
                case 6: limpa();
                consultaPorID(); break;
                case 7: limpa();
                salvarDadosEmArquivo(); break;
                case 0: System.out.println("SAINDO DO PROGRAMA. OBRIGADO!"); break;
                default: System.out.println("OPÇÃO INVÁLIDA.");
            }
        } while (opcao != 0);
    }
}
