#include <iostream>
#include <string.h>
#include "tinyxml2.h"

using namespace std;
using namespace tinyxml2;

#define FILE_PATH "/home/sysuser/project/MQTTClient.acr"

int main(int argc, char** argv) {
	cout<<"XML Modify"<<endl;
	const char* temp = argv[1];
	int period = atoi(temp);
	cout<<period<<endl;
	XMLDocument doc(FILE_PATH);
	XMLError check;
	if ((check = doc.LoadFile(FILE_PATH)) != XML_SUCCESS) {
		cout<<"Fail to open"<<endl;
		return 1;
	}
	XMLElement* root = doc.FirstChildElement();
	XMLElement* child = root->FirstChildElement("Config");
	while (child) {
		XMLElement* temp = child->FirstChildElement("Connect");
		if (temp->Attribute("x_device_id", "adam2")) {
			temp->SetAttribute("pub_period", period);
			cout<<"Modified";
			doc.SaveFile(FILE_PATH);
			system("reboot");
		}
		child = child->NextSiblingElement("Config");
	}
	cout<<"No Element";
	return 0;
}
