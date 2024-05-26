using System;
using System.Collections.Generic;
using System.Linq;

namespace Projektarbete
{
    class Program
    {
        static void Main(string[] args)
        {
            // Ett program för att spara maträtter och skapa slumpade veckomenyer med.
            //Vi har skrivit ca 2 metoder var och skrev metod Meny & SlumpMeny tillsammans.
            

            List<string> matrattLista = new List<string>() { };
            matrattLista.Add("SPAGHETTI MED KÖTTFÅRSSÅS");
            matrattLista.Add("PIZZA");
            matrattLista.Add("UGNSBAKAD LAX");
            matrattLista.Add("KORV STROGANOFF");
            matrattLista.Add("TACOS");
            matrattLista.Add("ÄRTSOPPA");
            matrattLista.Add("UGNSPANKAKA");

            // Listan av maträtter skapas i Main då den används av nästan alla metoder// Hannah.

            bool rattKod = false;
            string anvandarNamn;
            string losen;
            string programNamn = "Matguiden v1.0";
            string forfattare = " Hannah & Marco";

            Console.WriteLine("Välkommen!\n" + programNamn + "\nav " + forfattare + "\n");
            Console.WriteLine("Var god logga in:\n");

            while (rattKod == false) // Frågar om användare och lösen till LogIn() tills att användaren matar in rätt. bool från LogIn() returneras som true när rätt användare o lösen matas in.
            {
                Console.WriteLine("Användarnamn: ");
                anvandarNamn = Console.ReadLine();
                Console.WriteLine("Lösenord: ");
                losen = Console.ReadLine();
                rattKod = LogIn(anvandarNamn, losen);
            }
            //Inlogg och metoder // Marco

            Meny(matrattLista);



        }

        static bool LogIn(string anvandarnamnIn, string losenIn)
        {  //LogIn metod skriven av Marco
            bool godkantInlogg = false;
            string rattAnvandare1 = "admin";
            string rattLosen1 = "password";


            if (anvandarnamnIn == rattAnvandare1 && losenIn == rattLosen1)
            {
                godkantInlogg = true;
            }

            else
            {
                Console.WriteLine("Felaktigt användarnamn eller lösenord. Försök igen: ");
            }


            return godkantInlogg;
        }

        static void Meny(List<string> matrattLista)
        {
            //Metod för meny skriven av båda 

            int inmatVal;
            bool menyLoop = false;


            while (menyLoop == false)
            {
                try
                {
                    Console.WriteLine("\nMenyval: \n1. Maträtter \n2. Lägg till/Ta bort maträtt \n3. Ändra maträtt \n4. Slumpa veckomeny \n5. Avsluta");
                    inmatVal = int.Parse(Console.ReadLine());

                    if (inmatVal == 1)
                    {
                        menyLoop = true;
                        Matratter(matrattLista);
                    }

                    else if (inmatVal == 2)
                    {
                        menyLoop = true;
                        AddMatratt(matrattLista);
                    }

                    else if (inmatVal == 3)
                    {
                        menyLoop = true;
                        AndraMatratt(matrattLista);
                    }

                    else if (inmatVal == 4)
                    {
                        menyLoop = true;
                        SlumpaMeny(matrattLista);
                    }
                    else if (inmatVal == 5)
                    {
                        menyLoop = true;
                        Avsluta();
                    }
                }

                catch (FormatException)
                {
                    Console.WriteLine("Felaktigt inmatat värde! Försök igen...");
                }
            }

        }

        static void Matratter(List<string> matrattLista)
        { //Skriven av Hannah

            for (int i = 0; i < matrattLista.Count; i++)
            {
                Console.WriteLine("\n" + matrattLista[i]);
            }


            while (true)
            {
                Console.WriteLine("Tryck N för att återgå till Meny.");
                string nKnapp;
                nKnapp = Console.ReadLine();

                if (nKnapp.ToUpper() == "N")//Ändrar input till versaler.
                {
                    Meny(matrattLista);
                }

                else if (nKnapp.ToUpper() != "N") //Fångar upp alla inputs som inte är "N"
                {

                    Console.WriteLine("Felaktigt inmatat värde.");
                    continue;
                }
            }
        }

        static void AddMatratt(List<string> matrattLista)
        { //Skriven an Hannah
            string nyMatratt;
            string knapp;
            bool matrattsLoop = false;

            while (matrattsLoop == false)
            {
                Console.WriteLine("tryck A för att lägga till en maträtt i listan.\ntryck C för att ta bort en maträtt ur listan.\nTryck N för att återgå till huvudmenyn.");
                knapp = Console.ReadLine();


                if (knapp.ToUpper() == "A") //Lägger till en maträtt i listan. Input ändras alltid till versaler för enklare användning.
                {
                    Console.WriteLine("Lägg till din maträtt: ");
                    nyMatratt = Console.ReadLine();

                    matrattLista.Add(nyMatratt.ToUpper());
                }

                else if (knapp.ToUpper() == "C")//Tar bort en maträtt ur listan.
                {
                    Console.WriteLine("Nuvarande lista med maträtter: \n");
                    for (int i = 0; i < matrattLista.Count; i++)
                    {
                        Console.WriteLine(matrattLista[i]);  //Skriver ut nuvarande lista med maträtter som användaren kan ta bort.
                    }

                    Console.WriteLine("\nSkriv in maträtten du vill ta bort: ");
                    nyMatratt = Console.ReadLine();

                    matrattLista.Remove(nyMatratt.ToUpper());
                }

                else if (knapp.ToUpper() == "N") //Återgå till huvudmenyn.
                {
                    Meny(matrattLista);
                    matrattsLoop = true;
                }

                else //Fångar upp om användaren inte matat in ngt av alternativen, ist för try/catch då vi använder oss av string och programmet ej krashar vid felaktig inmatning.
                {
                    Console.WriteLine("Felaktigt inmatat värde!");
                    continue;
                }
            }


        }

        static void AndraMatratt(List<string> matrattLista)
        { //Skrivet av Marco
            string editRatt;
            string nyMatratt;
            string input0;
            string input1;
            string svar;
            bool matLoop;


            while (true)
            {
                Console.WriteLine("\nVilken maträtt vill du ändra?");
                input0 = Console.ReadLine();
                editRatt = input0.ToUpper(); //Alla maträtter ändras till stora bokstäver i koden för enklare användning.

                if (!matrattLista.Contains(editRatt)) //Om maträtten inte finns med i listan försök igen.
                {
                    Console.WriteLine("Kunde inte hitta någon maträtt med det namnet, försök igen!");
                    continue;
                }

                for (int i = 0; i < matrattLista.Count; i++)
                {
                    while (matrattLista.Contains(editRatt))
                    {
                        matLoop = true;

                        Console.WriteLine("Skriv en ny maträtt: ");
                        matrattLista.Remove(editRatt); //tar bort den gamla

                        nyMatratt = Console.ReadLine();
                        matrattLista.Add(nyMatratt.ToUpper());//Lägger till den gamla

                        while (matLoop == true)
                        {
                            Console.WriteLine("Vill du ändra en till maträtt skriv J, vill du gå tillbaka till menyn skriv N.");

                            input1 = Console.ReadLine();
                            svar = input1.ToUpper();

                            if (svar == "J")
                            {
                                matLoop = false;
                            }

                            else if (svar == "N")
                            {
                                Meny(matrattLista);
                            }
                            else //Om svar varken är "N" eller "J"
                            {
                                Console.Write("Felaktigt inmatat värde, försök igen...");
                            }
                        }

                    }
                }

            }
        }

        static void SlumpaMeny(List<string> matrattLista)
        {  
            //Multidimensionell array för att uppnå VG som båda skrev tillsammans.  
            Random rnd = new Random();
            string slumpadRatt;
            bool slumpBool = false;
            bool omLoop;
            string inmatKey;
            string[,] slumpArray = new string[7, 2];

            slumpArray[0, 0] = "Måndag: ";
            slumpArray[1, 0] = "Tisdag: ";
            slumpArray[2, 0] = "Onsdag: ";
            slumpArray[3, 0] = "Torsdag: ";
            slumpArray[4, 0] = "Fredag: ";
            slumpArray[5, 0] = "Lördag: ";
            slumpArray[6, 0] = "Söndag: ";

            while (slumpBool == false) 
            {
                omLoop = true; // Ändrar omloopen tillbaka till true så man inte fastnar i slumpBool.

                for (int i = 0; i < slumpArray.GetLength(0); i++) //Multidimensionell array som slumpar maträtter på rad 1 för SlumpaMeny metoden.
                {
                    int newRandom = rnd.Next(1, matrattLista.Count);
                    slumpadRatt = matrattLista[newRandom];
                    slumpArray[i, 1] = slumpadRatt;
                }

                Console.WriteLine("Veckans slumpade matschema: ");
                for (int i = 0; i < slumpArray.GetLength(0); i++) // i körs 7 ggr då dimension 0 är 7 rader lång
                {
                    for (int j = 0; j < slumpArray.GetLength(1); j++) // j körs 2 ggr då dim 1 är 2 kolumner lång
                    {
                        Console.WriteLine(slumpArray[i, j]);
                    }
                }

                while (omLoop == true)//Kör om koden tills användaren väljer att gå till menyn. 
                {
                    Console.WriteLine("Tryck Y för att slumpa ett nytt matschema för veckan eller tryck N för att återgå till Menyn.");
                    inmatKey = Console.ReadLine();


                    if (inmatKey.ToUpper() == "Y")
                    {
                        omLoop = false;
                        continue;
                    }

                    else if (inmatKey.ToUpper() == "N")
                    {
                        slumpBool = true;
                        Meny(matrattLista);
                    }

                    else
                    {
                        Console.WriteLine("Felaktigt inmatat värde.");
                        continue;
                    }
                }
            }

        }

        static void Avsluta()
        {   //Skrevs av Hannah
            Console.WriteLine("Hejdå!");
            return;
        }

    }
}
