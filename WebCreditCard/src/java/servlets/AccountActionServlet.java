/**
 *
 * @author KBowen
 * This is where you will do your work
 */
package servlets;

import business.CreditCard;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AccountActionServlet extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        String URL = "/CardTrans.jsp";
        String msg = "";
        String chgDesc = "";
        
        
        CreditCard card;
        int acctno;
        double transamt;
        String stringer;
        
        try
        {
            String path = getServletContext().getRealPath("/WEB-INF/") + "\\";
            card = (CreditCard) request.getSession().getAttribute("card");
            String action = request.getParameter("actiontype");
            
            
            if(card == null && !action.equalsIgnoreCase("NEW") && !action.equalsIgnoreCase("EXISTING")) //if both are jacked throw error
            {
                msg = "Action attempt on unopened account. <br>";
            }
            else
            {
                
                switch(action)
                {
                    case "new":
                            
                        card = new CreditCard(path);
                        if(card.getErrorStatus())
                        {
                            msg += "Account open Error: " + card.getErrorMessage() + "<br>";
                        }
                        else
                        {
                            msg += card.getActionMsg() + "<br>";
                        }
                        
                        break;
                        
                    case "existing":
                            if(card.getErrorStatus())
                            //if(act.trim().isEmpty())
                            {
                                msg += "Existing Account open Error: " + card.getErrorMessage() + "<br>";
                            }
                            else
                            {
                                stringer = request.getParameter("account");
                                acctno = Integer.parseInt(stringer);
                        
                                acctno = card.getAccountId();
                        
                                msg += card.getActionMsg() + "<br>";
                            }
                        break;
                        
                    case "charge":
                        
                            if(card.getErrorStatus())
                            {
                                msg += "Charge Error: " + card.getErrorMessage() + "<br>";
                            }
                            else
                            {
                                stringer = request.getParameter("cAmt");
                                String cmsg = request.getParameter("cDesc");
                    
                                transamt = Double.parseDouble(stringer);
                    
                                card.setCharge(transamt, cmsg);
                                msg += card.getActionMsg() + "<br>";
                            }
                        break;
                        
                    case "payment":
                        
                            if(card.getErrorStatus())
                            {
                                msg += "Payment Error: " + card.getErrorMessage() + "<br>";
                            }
                            else
                            {
                                stringer = request.getParameter("pAmt");
                                transamt = Double.parseDouble(stringer);
                                card.setPayment(transamt);
                                msg += card.getActionMsg() + "<br>";
                            }
                        
                        break;
                        
                    case "increase":
                            if(card.getErrorStatus())
                            {
                                msg += "Increase Error: " + card.getErrorMessage() + "<br>";
                            }
                            else
                            {
                                stringer = request.getParameter("cIncrease");
                                transamt = Double.parseDouble(stringer);
                                card.setCreditIncrease(transamt);
                                msg += card.getActionMsg() + "<br>";
                            }
                        break;
                        
                    case "interest":
                            if(card.getErrorStatus())
                            {
                                msg += "Intereset Rate Error: " + card.getErrorMessage() + "<br>";
                            }
                            else
                            {
                                stringer = request.getParameter("iRate");
                                transamt = Double.parseDouble(stringer);
                                card.setInterestCharge(transamt);
                                msg += card.getActionMsg() + "<br>";
                        
                            }
                        break;
                        
                    case "history":
                            URL = "/History.jsp";
                        break;
                        
                    default:
                        
                        msg += "Here be dragons: " + card.getErrorMessage() + "<br>";
                        break;
                    
                }
                
                                
                request.getSession().setAttribute("card", card);
                Cookie acct = new Cookie("acct", String.valueOf(card.getAccountId()));
                acct.setPath("/");
                acct.setMaxAge(60*2);
                response.addCookie(acct);
                
            }
        }
        catch (Exception e)
        {
            msg += "servlet error: " + e.getMessage() + "<br>";
        }
        
        request.setAttribute("msg", msg);
        RequestDispatcher disp = getServletContext().getRequestDispatcher(URL);
        
        disp.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
