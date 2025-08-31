package com.essj.ticketsystem;

import com.essj.ticketsystem.models.Ticket;
import com.essj.ticketsystem.models.User;
import com.essj.ticketsystem.models.enums.TicketPriority;
import com.essj.ticketsystem.models.enums.TicketStatus;
import com.essj.ticketsystem.models.enums.UserRole;
import com.essj.ticketsystem.repositories.TicketRepository;
import com.essj.ticketsystem.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class DataLoader implements CommandLineRunner {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public DataLoader(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {


        // --- 1. Criação dos Usuários ---

        User admin = new User("admin","1234","admin@email.com", UserRole.ADMIN);
        userRepository.save(admin);

        User user = new User("user","1234","user@email.com", UserRole.USER);
        userRepository.save(user);
        User savedUser = userRepository.save(user);


        User user2 = new User("user2","1234","user2@email.com", UserRole.USER);
        userRepository.save(user);
        User savedUser2 = userRepository.save(user2);


        User support = new User("support","1234","support@email.com", UserRole.SUPPORT_AGENT);
        userRepository.save(support);

        // --- 2. Criação dos Tickets ---

        List<Ticket> tickets = new ArrayList<>();

        tickets.add(new Ticket("Não consigo fazer login", "Ao tentar entrar no sistema com meu email e senha, recebo a mensagem 'Credenciais inválidas', mas tenho certeza que estão corretas.", TicketStatus.OPEN, TicketPriority.HIGH, savedUser));
        tickets.add(new Ticket("O botão de exportar PDF não funciona", "Na página de relatórios, o botão para exportar o relatório em PDF não realiza nenhuma ação quando clicado.", TicketStatus.OPEN, TicketPriority.MEDIUM, savedUser));
        tickets.add(new Ticket("Erro 500 ao salvar formulário de perfil", "Toda vez que tento atualizar minhas informações de perfil, a página retorna um erro interno do servidor (Erro 500).", TicketStatus.IN_PROGRESS, TicketPriority.HIGH, savedUser2));
        tickets.add(new Ticket("Lentidão ao carregar o dashboard", "O painel principal está demorando mais de 30 segundos para carregar todas as informações e gráficos.", TicketStatus.IN_PROGRESS, TicketPriority.MEDIUM, savedUser));
        tickets.add(new Ticket("Dúvida sobre a funcionalidade de agendamento", "Gostaria de saber como posso configurar agendamentos recorrentes para as tarefas semanais.", TicketStatus.RESOLVED, TicketPriority.LOW, savedUser2));
        tickets.add(new Ticket("O sistema desconecta sozinho", "Após alguns minutos de inatividade, o sistema me desloga automaticamente. É possível aumentar esse tempo?", TicketStatus.OPEN, TicketPriority.LOW, savedUser2));
        tickets.add(new Ticket("Problema na visualização de imagens", "As imagens que faço upload para a galeria do projeto não estão sendo exibidas, aparece um ícone de imagem quebrada.", TicketStatus.OPEN, TicketPriority.HIGH, savedUser));
        tickets.add(new Ticket("Relatório de vendas com valores incorretos", "O relatório de vendas do mês passado está mostrando um total diferente da soma dos valores individuais dos pedidos.", TicketStatus.CLOSED, TicketPriority.URGENT, savedUser2));
        tickets.add(new Ticket("Sugestão: Modo Noturno", "Seria ótimo se a aplicação tivesse uma opção de tema escuro (modo noturno) para facilitar o uso em ambientes com pouca luz.", TicketStatus.RESOLVED, TicketPriority.LOW, savedUser));
        tickets.add(new Ticket("Não recebo os e-mails de notificação", "Não estou recebendo as notificações por e-mail quando uma tarefa é atribuída a mim. Já verifiquei a caixa de spam.", TicketStatus.IN_PROGRESS, TicketPriority.MEDIUM, savedUser2));

        ticketRepository.saveAll(tickets);

        System.out.println("✅ Dados de usuários e tickets carregados com sucesso!");



    }
}
