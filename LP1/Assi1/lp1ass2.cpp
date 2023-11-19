#include <bits/stdc++.h>
#include <fstream>

using namespace std;

class Registers
{
    string reg;
    int no;
    map<string, string> regMap;

public:
    Registers()
    {
        this->reg = "";
        this->no = 0;
    }
    Registers(string a, int b)
    {
        this->reg = a;
        this->no = b;
    }
    friend class SourceTable;
};

class ConditionCode
{
    string stat;
    int len;
    map<string, string> conTable;

public:
    ConditionCode()
    {
        this->stat = "";
        this->len = 0;
    }
    ConditionCode(string a, int no)
    {
        this->stat = a;
        this->len = no;
    }
    friend class SourceTable;
};

class Mnemonic
{
    string name;
    string mne_class;
    int code;
    map<string, pair<string, string>> neoMap;

public:
    Mnemonic()
    {

        name = "";
        mne_class = "";
        code = -1;
    }
    Mnemonic(string nm, string cls, int cd)
    {

        name = nm;
        mne_class = cls;
        code = cd;
    }
    friend class SourceTable;
};

class Symbol
{
    string symboll;
    int addr;
    map<string, pair<int, string>> smbMap;

public:
    Symbol()
    {
        this->symboll = "";
        this->addr = 0;
    }
    Symbol(string a, int no)
    {
        this->symboll = a;
        this->addr = no;
    }
    friend class SourceTable;
};
class Literal
{
    string literal;
    int addr;
    map<string, pair<int, string>> litMap;

public:
    Literal()
    {
        this->literal = "";
        this->addr = 0;
    }
    Literal(string a, int no)
    {
        this->literal = a;
        this->addr = no;
    }
    friend class SourceTable;
};

class SourceTable
{
    Registers regO;
    ConditionCode conO;
    Mnemonic mnO;
    Symbol smbO;
    Literal ltrO;

    fstream f;

public:
    SourceTable()
    {
        // create tables of ---->
        regO.regMap.insert({"AREG", "01"});
        regO.regMap.insert({"BREG", "02"});
        regO.regMap.insert({"CREG", "03"});
        regO.regMap.insert({"DREG", "04"});

        conO.conTable.insert({"LT", "1"});
        conO.conTable.insert({"LE", "2"});
        conO.conTable.insert({"EQ", "3"});
        conO.conTable.insert({"GE", "4"});
        conO.conTable.insert({"GT", "5"});
        conO.conTable.insert({"ANY", "6"});

        mnO.neoMap.insert({"STOP", {"IS", "00"}});
        mnO.neoMap.insert({"ADD", {"IS", "01"}});
        mnO.neoMap.insert({"SUB", {"IS", "02"}});
        mnO.neoMap.insert({"MULT", {"IS", "03"}});
        mnO.neoMap.insert({"MOVER", {"IS", "04"}});
        mnO.neoMap.insert({"MOVEM", {"IS", "05"}});
        mnO.neoMap.insert({"COMP", {"IS", "06"}});
        mnO.neoMap.insert({"BC", {"IS", "07"}});
        mnO.neoMap.insert({"DIV", {"IS", "08"}});
        mnO.neoMap.insert({"READ", {"IS", "09"}});
        mnO.neoMap.insert({"PRINT", {"IS", "10"}});
        mnO.neoMap.insert({"START", {"AD", "01"}});
        mnO.neoMap.insert({"END", {"AD", "02"}});
        mnO.neoMap.insert({"ORIGIN", {"AD", "03"}});
        mnO.neoMap.insert({"EQU", {"AD", "04"}});
        mnO.neoMap.insert({"LTORG", {"AD", "05"}});
        mnO.neoMap.insert({"DC", {"DL", "01"}});
        mnO.neoMap.insert({"DS", {"DL", "02"}});
    }

    string perform_Operation(int &n, string lb, string opc, string op1, string op2, string &IcOp1)
    {
        // n is lc taken from takeFile()
        string currLC = "";

        if (opc == "START")
        {
            return "";
        }
        if (opc == "END")
        {
            return to_string(n++);
        }
        if (opc == "LTORG")
        {
            // assign addresses to literals until no. of literals end
            for (auto it = ltrO.litMap.begin(); it != ltrO.litMap.end(); it++)
            {
                it->second.second = to_string(n++);
            }
            return "";
        }
        if (opc == "ORIGIN")
        {
            // check op1
            // part1: before + part2: after +
            string beforeSign = "", afterSign = "";
            int i = 0;
            int signInd = op1.find('+');
            if (signInd == string::npos)
            {
                signInd = op1.find('-');
                if (signInd == string::npos)
                {
                    beforeSign = op1;
                }
            }
            else
            {
                beforeSign = op1.substr(0, signInd);
                afterSign = op1.substr(signInd + 1);
            }

            int toAdd = 0;
            if (afterSign != "")
                toAdd = stoi(afterSign);

            n = stoi(smbO.smbMap[beforeSign].second) + toAdd;

            // for IcOp1
            IcOp1 = "(S," + to_string(smbO.smbMap[beforeSign].first) + ")+" + afterSign;
            return "";
        }
        if (opc == "EQU")
        {
            return "";
        }
        else
        {
            return to_string(n++);
        }
    }

    void literal_Symbol_FileEntry()
    {
        fstream fout, fout1;
        fout.open("literalTab.txt", ios::out);
        fout1.open("symbolTab.txt", ios::out);

        for (auto it = ltrO.litMap.begin(); it != ltrO.litMap.end(); it++)
        {
            fout << it->second.first << " " << it->first << "  " << it->second.second << "\n";
        }
        for (auto it = smbO.smbMap.begin(); it != smbO.smbMap.end(); it++)
        {
            fout1 << it->second.first << "  " << it->first << "  " << it->second.second << "\n";
        }
        fout.close();
        fout1.close();
    }

    void takeFileIn()
    {
        // must update symboltable and literalTable.

        string lb = "";
        string opc = "", op1 = "", op2 = "";
        int LC = INT_MIN;

        f.open("sourceFile.txt", ios::in);
        f.seekg(0, ios::beg);

        ofstream p1Out;
        p1Out.open("p1Out.txt", ios::out);
        // cout<<"Provide source in UPPERCASE && Enter - for empty space"<<endl;
        cout << left << setw(12) << "LABEL" << left << setw(12) << "OPCODE" << left << setw(12) << "OP1" << left << setw(12) << "OP2" << left << setw(6) << "|" << left << setw(6) << "LC" << left << setw(12) << "IC OPCODE" << left << setw(12) << "IC OP1" << left << setw(12) << "IC OP2" << endl;
        cout << "------------------------------------------------|-----------------------------------------" << endl;

        p1Out << left << setw(12) << "LABEL" << left << setw(12) << "OPCODE" << left << setw(12) << "OP1" << left << setw(12) << "OP2" << left << setw(6) << "|" << left << setw(6) << "LC" << left << setw(12) << "IC OPCODE" << left << setw(12) << "IC OP1" << left << setw(12) << "IC OP2" << endl;
        p1Out << "------------------------------------------------|-----------------------------------------" << endl;
        ;

        while (!f.eof())
        {
            f >> lb;
            f >> opc;
            f >> op1;
            f >> op2;
            // get start adress
            // print LC value
            if (opc == "START")
            {
                LC = stoi(op1);
            }

            // when inputed from file, if empty-->(in the source section)
            if (lb == "-")
                lb = "";
            if (opc == "-")
                opc = "";
            if (op1 == "-")
                op1 = "";
            if (op2 == "-")
                op2 = "";

            // find ic for respective-->>
            // if not found in map then print empty string
            string IcOpc;
            if (mnO.neoMap.find(opc) == mnO.neoMap.end())
            {
                IcOpc = "";
            }
            else
            {
                IcOpc = "(" + mnO.neoMap[opc].first + "," + mnO.neoMap[opc].second + ")";
            }

            // can be reg or conditional or empty
            string IcOp1;
            if (regO.regMap.find(op1) != regO.regMap.end())
            {
                IcOp1 = "(" + regO.regMap[op1] + ")";
            }
            else if (conO.conTable.find(op1) != conO.conTable.end())
            {
                IcOp1 = "(" + conO.conTable[op1] + ")";
            }
            else if (opc == "DC")
            {
                IcOp1 = "(C," + op1 + ")";
            }
            else if (opc == "DS")
            {
                IcOp1 = "(C," + op1 + ")";
                LC += stoi(op1) - 1;
            }
            else
            {
                IcOp1 = "";
            }

            // for icop2

            // perform entry to literal symbol table
            if (op2[0] == '=')
            {
                // if literal not in table
                if (ltrO.litMap.find(op2) == ltrO.litMap.end())
                {
                    ltrO.litMap.insert({op2, {ltrO.litMap.size() + 1, to_string(LC)}});
                }
                if ((smbO.smbMap.find(lb) == smbO.smbMap.end() && lb != ""))
                {
                    smbO.smbMap.insert({lb, {smbO.smbMap.size() + 1, to_string(LC)}});
                }
                // else assign address to alredy existing symbol(lb)
                else if (smbO.smbMap.find(lb) != smbO.smbMap.end())
                {
                    smbO.smbMap[lb].second = to_string(LC);
                }
            }
            else // its a symbol (label is also symbol which specifies address of symbol,  op2 is used only to make symbol entry to symtable)
            {
                // if symbol not in symbol table
                // if label not in map enter with adress
                if ((smbO.smbMap.find(lb) == smbO.smbMap.end() && lb != ""))
                {
                    smbO.smbMap.insert({lb, {smbO.smbMap.size() + 1, to_string(LC)}});
                }
                // else assign address to alredy existing symbol(lb)
                else if (smbO.smbMap.find(lb) != smbO.smbMap.end())
                {
                    smbO.smbMap[lb].second = to_string(LC);
                }
                if ((smbO.smbMap.find(op2) == smbO.smbMap.end() && op2 != ""))
                {
                    smbO.smbMap.insert({op2, {smbO.smbMap.size() + 1, to_string(LC)}});
                }
            }
            // Now print IcOp2 in output
            string IcOp2 = "";
            if (op2[0] == '=')
            {
                // if literal not in table
                IcOp2 = "(L," + to_string(ltrO.litMap[op2].first) + ")";
            }
            else // its a symbol
            {
                // if symbol not in symbol table
                if (op2 != "")
                {
                    IcOp2 = "(S," + to_string(smbO.smbMap[op2].first) + ")";
                }
            }
            // executing performOperation function()-->
            string LC_string = perform_Operation(LC, lb, opc, op1, op2, IcOp1);

            cout << left << setw(12) << lb << left << setw(12) << opc << left << setw(12) << op1 << left << setw(12) << op2 << left << setw(6) << "|" << left << setw(6) << LC_string << left << setw(12) << IcOpc << left << setw(12) << IcOp1 << left << setw(12) << IcOp2 << endl;
            p1Out << left << setw(12) << lb << left << setw(12) << opc << left << setw(12) << op1 << left << setw(12) << op2 << left << setw(6) << "|" << left << setw(6) << LC_string << left << setw(12) << IcOpc << left << setw(12) << IcOp1 << left << setw(12) << IcOp2 << endl;
        }
        literal_Symbol_FileEntry();
        p1Out.close();
        f.close();
    }
};

int main()
{
    SourceTable src;
    src.takeFileIn();
    return 0;
}