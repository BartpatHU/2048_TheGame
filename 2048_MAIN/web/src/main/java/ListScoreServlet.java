import hu.projekt.dao.ScoreDAO;
import hu.projekt.dao.ScoreDAOImpl;
import hu.projekt.model.Score;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/scores")
public class ListScoreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html");
        ScoreDAO dao = new ScoreDAOImpl();
        PrintWriter pw = resp.getWriter();

        for (Score s : dao.listALL()){
            pw.println(s);
        }
    }
}
