#include <iostream>
#include <fstream>
#include <sstream>

using namespace std;

int main(int argc, char** argv) {

	// argv[1] :- Test file
	// argv[2] :- Output1 file
	// argv[3] :- Output2 file

	if (argc < 4) {
		cout << "Insufficient arguments" << endl;
		return 0;
	}

	ifstream test(argv[1]);
	ifstream output1(argv[2]);
	ifstream output2(argv[3]);

	cout << argv[1] << " " << argv[2] << " " << argv[3] << endl;
	int lineNum = 0;
	string line;

	while (getline(test, line)) {

		lineNum += 1;
		istringstream iss(line);

		int temp;
		if (!(iss >> temp)) {

			int o1, o2;
			if ((output1 >> o1) && (output2 >> o2)) {
			//	cout << temp << " " << o1 << " " << o2 << endl;
				if (o1 != o2) {
					cout << "Output not matched for input at Line " << lineNum << 
							"          " << line << "          " << o1 << " " << o2 << endl;
				}
			} else {
				cout << "cannot compare output of line " << lineNum << endl;
				return 0;
			}
		}

	}

}