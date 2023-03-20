using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WebSocketSharp;
using WebSocketSharp.Server;

namespace Websocket_Server
{
    internal class Program
    {
        //public class Echo : WebSocketBehavior
        //{
        //    protected override void OnMessage(MessageEventArgs e)
        //    {
        //        Console.WriteLine("Received message from client: " + e.Data);
        //        Send("InkassoProcessInfo{infoType=START_PROCESS, operatorPortalId='12012412', \r\napplicationId='ISPID', " +
        //            "\r\nbusinessId='000000000', \r\nbusinessCaseTranslation='ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.CodeType@471584d9[translation={ch.admin.ejpd." +
        //            "sysp.resources.biometrics.enrolment.common.v1.TranslationType@2d08ddf6[value=ID, languageKey=de],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1." +
        //            "TranslationType@76d27db3[value=ID, \r\nlanguageKey=fr],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@1d8a1cfc[value=NA, languageKey=en]," +
        //            "\r\nch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@50bfd48d[value=ID,languageKey=it]}, \r\ncodeID=ANTRAG_TYP_ID03]', \r\nname='Aeby'," +
        //            " \r\nfirstNames='Annigna Lea', \r\ndateOfBirth='28.03.1990', \r\nplaceOfBirth='Bern BE', \r\nhomeTown='Bern BE', \r\nlanguage='de', \r\naddress=ch.admin." +
        //            "ejpd.sysp.resources.biometrics.enrolment.auftrag.v1.AddressType@3bb35401[title=ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1." +
        //            "CodeType@5ee99921[translation={ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@134913fc[value=Frau, languageKey=de]}," +
        //            " \r\ncodeID=Frau], \r\nname=Aeby, \r\nfirstNames=Anna, \r\nadditionalInformation=<null>(default), \r\nstreet=Spitzmattstrasse, \r\nstreetNumber=12," +
        //            " \r\nzip=3155, \r\ncity=Worb, \r\ncountry=ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.CodeType@3dfcb6a4[translation={ch.admin.ejpd." +
        //            "sysp.resources.biometrics.enrolment.common.v1.TranslationType@1cceee46[value=CHE, languageKey=de],ch.admin.ejpd.sysp.resources.biometrics.enrolment." +
        //            "common.v1.TranslationType@e7c4c6f[value=CHE, languageKey=fr],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@55f168af[value=CHE," +
        //            " languageKey=en],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@de0bd23[value=CHE, languageKey=it]}, codeID=CHE]]}");
        //    }
        //}

        //static void Main(string[] args)
        //{
        //    WebSocketServer wssv = new WebSocketServer("ws://127.0.0.1:7890");

        //    wssv.AddWebSocketService<Echo>("/Echo");

        //    wssv.Start();
        //    Console.WriteLine("Server starten on ws://127.0.0.1:7890/Echo");

        //    Console.ReadKey();
        //    wssv.Stop();
        //}

        static void Main(string[] args)
        {
            var server = new WebSocketServer("ws://localhost:7890");
            server.AddWebSocketService<Echo>("/echo");
            server.Start();
            Console.WriteLine("WebSocket server started at ws://localhost:7890/echo");
            Console.ReadKey(true);
            server.Stop();
        }

        public class Echo : WebSocketBehavior
        {
            protected override void OnMessage(MessageEventArgs e)
            {
                Console.WriteLine("Received message from client: " + e.Data);
                Send("\"InkassoProcessInfo{infoType=START_PROCESS, operatorPortalId='12012412', \\r\\napplicationId='ISPID', \" +\r\n " +
                    "\"\\r\\nbusinessId='000000000', \\r\\nbusinessCaseTranslation='ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.CodeType@471584d9[translation={ch.admin.ejpd.\" +\r\n " +
                    "\"sysp.resources.biometrics.enrolment.common.v1.TranslationType@2d08ddf6[value=ID, languageKey=de],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.\" +\r\n " +
                    "\"TranslationType@76d27db3[value=ID, \\r\\nlanguageKey=fr],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@1d8a1cfc[value=NA, languageKey=en],\" +\r\n  " +
                    " \"\\r\\nch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@50bfd48d[value=ID,languageKey=it]}, \\r\\ncodeID=ANTRAG_TYP_ID03]', \\r\\nname='Aeby',\" +\r\n" +
                    "   \" \\r\\nfirstNames='Annigna Lea', \\r\\ndateOfBirth='28.03.1990', \\r\\nplaceOfBirth='Bern BE', \\r\\nhomeTown='Bern BE', \\r\\nlanguage='de', \\r\\naddress=ch.admin.\" +\r\n " +
                    " \"ejpd.sysp.resources.biometrics.enrolment.auftrag.v1.AddressType@3bb35401[title=ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.\" +\r\n" +
                    " \"CodeType@5ee99921[translation={ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@134913fc[value=Frau, languageKey=de]},\" +\r\n " +
                    " \" \\r\\ncodeID=Frau], \\r\\nname=Aeby, \\r\\nfirstNames=Anna, \\r\\nadditionalInformation=<null>(default), \\r\\nstreet=Spitzmattstrasse, \\r\\nstreetNumber=12,\" +\r\n" +
                    "  \" \\r\\nzip=3155, \\r\\ncity=Worb, \\r\\ncountry=ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.CodeType@3dfcb6a4[translation={ch.admin.ejpd.\" +\r\n" +
                    " \"sysp.resources.biometrics.enrolment.common.v1.TranslationType@1cceee46[value=CHE, languageKey=de],ch.admin.ejpd.sysp.resources.biometrics.enrolment.\" +\r\n " +
                    "\"common.v1.TranslationType@e7c4c6f[value=CHE, languageKey=fr],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@55f168af[value=CHE,\" +\r\n " +
                    "\" languageKey=en],ch.admin.ejpd.sysp.resources.biometrics.enrolment.common.v1.TranslationType@de0bd23[value=CHE, languageKey=it]}, codeID=CHE]]}\");\r\n            " + e.Data);}
        }
    }
}
