//
// Created by floris on 6/9/25.
//

#ifndef UTIL_H
#define UTIL_H
#include <istream>
#include <regex>
#include <vector>
#include "util.h"


namespace util {

    typedef std::vector<std::vector<char>> charMap;
    typedef std::vector<std::string> stringArr;


    charMap convertToMap(std::istream& input);
    stringArr convertToArr(std::istream& input);

template <typename... Types>
void regex_assign(std::string &line, std::string regex, Types&... vars);


template <typename... Types>
void _assign_match(std::smatch& regex_match, int index) {}

template <typename... Types>
void _assign_match(std::smatch &match,int index, std::string& Arg,Types&... vars) {
    if (match.empty()) return;
    auto str = match[index].str();
    Arg = str;
    _assign_match(match, index + 1, vars...);
}

template <typename... Types>
void _assign_match(std::smatch &match,int index, std::vector<std::string>& Arg,Types&... vars) {
    if (match.empty()) return;
    index--;
    const size_t matches = match.size();
    for (int i = index; i <= matches; i++) {
        auto str = match[index].str();
        Arg.push_back(str);
    }
    _assign_match(match, index + 1, vars...);
}



template <typename... Types>
void _assign_match(std::smatch &match,int index, int& Arg,Types&... vars) {
    if (match.empty()) return; // sanity check
    auto str = match[index].str();
    Arg = std::stoi(str);
    _assign_match(match, index + 1, vars...);
}

template <typename... Types>
void _assign_match(std::smatch &match,int index, long& Arg,Types&... vars) {
    if (match.empty()) return; // sanity check
    auto str = match[index].str();
    Arg = std::stoi(str);
    _assign_match(match, index + 1, vars...);
}

/**
 * Assigns all captures groups that are matched in a string to the
 * given variables
 * @tparam Types Currently supprted types: Long, Int, String
 * @param line The thing you want to match on
 * @param regex  The regex parttern WITH CAPTURE GROUPS you want to match
 * @param vars All variables the captures groups will assigned to
 */
template <typename... Types>
void util::regex_assign(std::string &line, std::string regex, Types&... vars) {
    std::regex regex_pat{regex};
    std::smatch match;
    std::regex_search(line, match, regex_pat);
    _assign_match(match, 1, vars...);

}
}

#endif //UTIL_H
