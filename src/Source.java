import java.util.Scanner;

public class Source {

    public static Scanner scanner = new Scanner(System.in);

    static class InfToOnpConverter {

        public static int PriorityINF_ONP ( char chr ) {
            // Set priority of operators for infix to RPN conversion
            if (chr == '=') {
                return 0;
            } else if (chr == '|' ) {
                return 1;
            } else if (chr == '&' ) {
                return 2;
            } else if (chr == '<' || chr == '>') {
                return 3;
            } else if (chr == '+' || chr == '-') {
                return 4;
            } else if (chr == '*' || chr == '/' || chr == '%') {
                return 5;
            } else if (chr == '^') {
                return 6;
            } else if (chr == '!' || chr == '~') {
                return 7;
            } else {
                return -1;
            }
        }

        public static boolean IsLeft_Hand ( char chr ) {
            // Check if the operator is left associativity
            if (chr == '=') {
                return false;
            } else if (chr == '|' || chr == '&') {
                return true;
            } else if (chr == '<' || chr == '>') {
                return true;
            } else if (chr == '+' || chr == '-') {
                return true;
            } else if (chr == '*' || chr == '/' || chr == '%') {
                return true;
            } else if (chr == '^') {
                return false;
            } else if (chr == '!' || chr == '~') {
                return false;
            }
            else return false;
        }

        public static String IsCorrectINF ( String inf ) {
            // Validate infix notation
            String correctINF = "";
            char chr;
            int i;
            for (i = 0; i < inf.length(); i++) {
                chr = inf.charAt(i);
                if ((chr == '=') || (chr == '|') || (chr == '&') || (chr == '<')
                        || (chr == '>') || (chr == '+') || (chr == '-') || (chr == '*')
                        || (chr == '/') || (chr == '%') || (chr == '^') || (chr == '!')
                        || (chr == '~') || (chr <= 'z' && chr >= 'a') || (chr == ')') || (chr == '(')) {
                    correctINF = correctINF + chr;
                }
            }

            int TuringQ = 0;
            int bracketsCounter = 0;
            boolean isError = false;

            for ( i = 0; i < correctINF.length(); i++ ) {
                chr = correctINF.charAt( i );


                if ( TuringQ == 0 ) {
                    if (( chr == '!') || ( chr == '~' ))
                        TuringQ = 2;
                    else if ( chr == '(' ) {
                        TuringQ = 0;
                        if ( bracketsCounter >= 0 )
                            bracketsCounter++;
                        else
                            isError = true;
                    }
                    else if (( chr <= 'z' ) && ( chr >= 'a' )) 
                        TuringQ = 1;
                    else
                        isError = true;
                }
                else if ( TuringQ == 1 ) {
                    if ((chr == '=') || (chr == '|') || (chr == '&') || (chr == '<')
                            || (chr == '>') || (chr == '+') || (chr == '-') || (chr == '*')
                            || (chr == '/') || (chr == '%') || (chr == '^')) {
                        TuringQ = 0;
                    }
                    else if ( chr == ')' ) {
                        TuringQ = 1;
                        if ( bracketsCounter > 0 )
                            bracketsCounter--;
                        else
                            isError = true;
                    }
                    else
                        isError = true;
                }
                else if ( TuringQ == 2 ) {
                    if (( chr <= 'z' ) && ( chr >= 'a' ))
                        TuringQ = 1;
                    else if (( chr == '~') || ( chr == '!' ))
                        TuringQ = 2;
                    else if ( chr == '(' ) {
                        if ( bracketsCounter < 0 )
                            isError = true;
                        else
                            bracketsCounter++;
                        TuringQ = 0;
                    }
                }
            }

            chr = correctINF.charAt( correctINF.length()-1 );
            if ((chr == '=') || (chr == '|') || (chr == '&') || (chr == '<')
                    || (chr == '>') || (chr == '+') || (chr == '-') || (chr == '*')
                    || (chr == '/') || (chr == '%') || (chr == '^') || (chr == '!')
                    || (chr == '~')) {
                isError = true;
            }
            if ( bracketsCounter != 0 )
                isError = true;
            if ( isError )
                return "error";
            else
                return correctINF;
        }


        public static String INFtoONP(String str) {
            // Convert from infix to RPN notation
            String[] stack = new String[str.length()];
            int stIdx = -1;
            String onp = "";
            char chr;

            int i;
            for ( i = 0; i < str.length(); i++ ) {
                chr = str.charAt(i);

                if ( chr <= 'z' && chr >= 'a' ) {
                    onp = onp + chr + " ";
                } else if (chr == '(') {
                    stIdx++;
                    stack[ stIdx ] = String.valueOf( chr );
                }
                else if (( chr == '=' ) || ( chr == '|' ) || ( chr == '&' ) || ( chr == '<' )
                        || ( chr == '>' ) || ( chr == '+' ) || ( chr == '-' ) || ( chr == '*' )
                        || ( chr == '/' ) || ( chr == '%' ) || ( chr == '^' ) || ( chr == '!' )
                        || ( chr == '~' ))  {
                    if ( ! ( stIdx < 0 )) {
                        String operation = stack[ stIdx ];
                        char chrOp = operation.charAt( 0 );

                        int currPriority = PriorityINF_ONP( chr );
                        int lastPriority = PriorityINF_ONP( chrOp);
                        boolean isLeftHand = IsLeft_Hand( chr );

                        while ( ((( isLeftHand == false ) && ( lastPriority > currPriority ))
                                || (( isLeftHand == true ) && ( lastPriority >= currPriority ))) && ( lastPriority != -1 )) {
                            onp = onp + stack[ stIdx ] + " ";
                            stIdx--;
                            if ( stIdx < 0 ) {
                                break;
                            } else {
                                operation = stack[ stIdx ];
                                lastPriority  = PriorityINF_ONP( operation.charAt( 0 ));
                            }
                        }
                    }
                    stIdx++;
                    stack[ stIdx ] = String.valueOf( chr );
                } else if (chr == ')') {
                    if ( ! ( stIdx < 0 )) {
                        String lastOperator = stack[ stIdx ];
                        stIdx--;
                        while ( !lastOperator.equals( "(" ) ) {
                            onp = onp + lastOperator + " ";
                            lastOperator = stack[stIdx];
                            stIdx--;
                        }
                    }
                }
            }
            stIdx++;
            while ( stIdx --> 0 ) {
                onp = onp + stack[ stIdx ] + " ";
            }
            return onp;
        }

    }

    static class OnpToInfConverter {

        public static int PriorityINF_ONP ( char chr ) {
            // Set priority of operators in infix expressions
            if (chr == '=') {
                return 1;
            } else if (chr == '|' || chr == '&') {
                return 2;
            } else if (chr == '<' || chr == '>') {
                return 3;
            } else if (chr == '+' || chr == '-') {
                return 4;
            } else if (chr == '*' || chr == '/' || chr == '%') {
                return 5;
            } else if (chr == '^') {
                return 6;
            } else if (chr == '!' || chr == '~') {
                return 7;
            } else if (chr >= 'a' && chr <= 'z') {
                return 8;
            } else {
                return -1;
            }
        }


        public static String IsCorrectONP( String onp ) {
            // Validate RPN notation
            String correctONP = "";
            char chr;
            int i;

            for (i = 0; i < onp.length(); i++) {
                chr = onp.charAt(i);

                if ((chr == '=') || (chr == '|') || (chr == '&') || (chr == '<')
                        || (chr == '>') || (chr == '+') || (chr == '-') || (chr == '*')
                        || (chr == '/') || (chr == '%') || (chr == '^') || (chr == '!')
                        || (chr == '~') || (chr <= 'z' && chr >= 'a')) {
                    correctONP = correctONP + chr;
                }
            }

            int correctnessCounter = 0;
            for (i = 0; i < correctONP.length(); i++) {
                chr = correctONP.charAt(i);
                if (chr <= 'z' && chr >= 'a')
                    correctnessCounter++;
                else if ((chr == '=') || (chr == '|') || (chr == '&') || (chr == '<')
                        || (chr == '>') || (chr == '+') || (chr == '-') || (chr == '*')
                        || (chr == '/') || (chr == '%') || (chr == '^')) {
                    correctnessCounter--;
                }
            }

            if (correctnessCounter != 1)
                return "error";
            else
                return correctONP;
        }


        public static String ONPtoINF( String onp ) {
            // Convert from RPN to infix notation
            int[] prioritiesStack = new int[ onp.length() ];
            String[] calculationsStack = new String[onp.length()];
            int pIdx = -1;
            int clIdx = -1;
            String tempCalculations = "";
            char chr;

            int i;
            for ( i = 0; i < onp.length(); i++ ) {
                chr = onp.charAt(i);
                if (( chr <= 'z' ) && ( chr >= 'a' )) {
                    clIdx++;
                    calculationsStack[clIdx] = String.valueOf(chr);
                    pIdx++;
                    prioritiesStack[pIdx] = 8;
                } else {
                    tempCalculations = "";
                    if (( chr == '~' ) || ( chr == '!' )) {
                        if ( prioritiesStack[ pIdx ] >= PriorityINF_ONP( chr )) {
                            tempCalculations = chr + " " + calculationsStack[clIdx];
                            clIdx--;
                            pIdx--;
                        } else {
                            tempCalculations = chr + " " + "( " + calculationsStack[clIdx] + " )";
                            clIdx--;
                            pIdx--;
                        }
                    }
                    else if (( chr == '=' ) || ( chr == '|' ) || ( chr == '&' ) || ( chr == '<' )
                            || ( chr == '>' ) || ( chr == '+' ) || ( chr == '-' ) || ( chr == '*' )
                            || ( chr == '/' ) || ( chr == '%' ) || ( chr == '^' )) {

                        if (( prioritiesStack[ pIdx ] <= PriorityINF_ONP( chr ) )) {
                            if (( (chr == '^') && prioritiesStack[pIdx] == PriorityINF_ONP(chr))) { // jesli tak
                                tempCalculations = calculationsStack[clIdx];
                                clIdx--;
                                pIdx--;
                            } else {
                                tempCalculations = "( " + calculationsStack[clIdx] + " )";
                                clIdx--;
                                pIdx--;
                            }
                        } else {
                            tempCalculations = calculationsStack[clIdx];
                            clIdx--;
                            pIdx--;
                        }

                        if ( prioritiesStack[ pIdx ] < PriorityINF_ONP( chr ) ) {
                            tempCalculations = "( " + calculationsStack[ clIdx ] + " ) " + chr + " " + tempCalculations;
                            clIdx--;
                            pIdx--;
                        } else {
                            tempCalculations = calculationsStack[ clIdx ] + " " + chr + " " + tempCalculations;
                            clIdx--;
                            pIdx--;
                        }
                    }

                    clIdx++;
                    pIdx++;
                    calculationsStack[ clIdx ] = tempCalculations;
                    prioritiesStack[ pIdx ] = PriorityINF_ONP( chr );
                }
            }
            String inf = calculationsStack[ clIdx ];
            return inf;
        }
    }


    public static void main(String[] args) {
        String inf = "INF: ";
        String onp = "ONP: ";

        int numberOfOperations = scanner.nextInt();
        scanner.nextLine();

        while ( numberOfOperations --> 0 ) {
            String inputData = scanner.nextLine();
            String identyficator = inputData.substring(0, 1);

            if ( identyficator.contains( "I" )) {
                inputData = InfToOnpConverter.IsCorrectINF( inputData );
                if ( !(inputData.equals("error")) )
                    inputData = InfToOnpConverter.INFtoONP( inputData );
                System.out.println( onp + inputData );
            }
            else if ( identyficator.contains( "O" )) {
                inputData = OnpToInfConverter.IsCorrectONP( inputData );
                if ( !(inputData.equals("error")) )
                    inputData = OnpToInfConverter.ONPtoINF( inputData );

                System.out.println( inf + inputData );
            }

        }

        scanner.close();
    }
}

//input
//20
//ONP: a b + c *
//ONP: d e f * + g -
//ONP: h i j k * / - l m * +
//ONP: n o ^ p +
//ONP: q r s - ^ t *
//ONP: u v w * x + y z * - +
//ONP: a b * c d * + e f * - g h * +
//ONP: i j / k ^ l m ^ n * +
//ONP: o p ^ q r * s t u v * + ^ +
//ONP: w x y ^ z a b * + ^ c d +
//INF: a + b * c
//INF: d + e * f - g
//INF: h - i / j * k + l * m
//INF: n ^ o + p
//INF: q ^ ( r - s ) * t
//INF: ( u * v + x ) - y * z + w
//INF: a * b + c * d - e * f + g * h
//INF: ( i / j ) ^ k + l ^ m * n
//INF: o ^ p * q + r * ( s + t * u * v )
//INF: ( w ^ x ) ^ ( y + z * a * b ) + c + d

//output
//INF: ( a + b ) * c
//INF: d + e * f - g
//INF: h - i / ( j * k ) + l * m
//INF: n ^ o + p
//INF: q ^ ( r - s ) * t
//INF: u + ( v * w + x - y * z )
//INF: a * b + c * d - e * f + g * h
//INF: ( i / j ) ^ k + l ^ m * n
//INF: error
//INF: error
//ONP: a b c * +
//ONP: d e f * + g -
//ONP: h i j / k * - l m * +
//ONP: n o ^ p +
//ONP: q r s - ^ t *
//ONP: u v * x + y z * - w +
//ONP: a b * c d * + e f * - g h * +
//ONP: i j / k ^ l m ^ n * +
//ONP: o p ^ q * r s t u * v * + * +
//ONP: w x ^ y z a * b * + ^ c + d +