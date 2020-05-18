#include <iostream>
#include <string.h>
#include "tinyxml2.h"

using namespace std;
using namespace tinyxml2;

#define FILE_PATH "MQTTClient.acr"

int main(int argc, char** argv) {
	XMLDocument doc(FILE_PATH);
	XMLError check;
	if ((check = doc.LoadFile(FILE_PATH)) != XML_SUCCESS) {
		cout<<"Fail to open";
		return 1;
	}
	XMLElement* root = doc.FirstChildElement();
	XMLElement* child = root->FirstChildElement("Config")->FirstChildElement("Connect");
	child->SetAttribute("New Attribute", "N4M");
	child->SetAttribute("Price", 8888);
	doc.SaveFile(FILE_PATH);
	return 0;
}
