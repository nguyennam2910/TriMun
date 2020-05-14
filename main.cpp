#include <iostream>
#include <string.h>
#include "tinyxml2.h"

using namespace std;
using namespace tinyxml2;

#define FILE_PATH "D:/tinyxml2/dream.xml"

int main(int argc, char** argv) {
	XMLDocument doc(FILE_PATH);
	XMLError check;
	if ((check = doc.LoadFile(FILE_PATH)) != XML_SUCCESS) {
		cout<<"Fail to open";
		return 1;
	}
	XMLElement* root = doc.FirstChildElement();
	XMLElement* child = root->FirstChildElement("PERSONAE")->FirstChildElement("PERSONA");
	while (child = child->NextSiblingElement("PERSONA")) {
		if (strcmp(child->GetText(),"SNOUT, a tinker.") == 0) {
			child->SetAttribute("name", "N4M");
			child->SetAttribute("next",18);
			child->SetAttribute("price", "9000");
			cout<<child->FindAttribute("name")->Value();
			break;
		}
	}
	doc.SaveFile(FILE_PATH);
	return 0;
}
