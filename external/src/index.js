import * as i0 from "blockies-ts";
import * as i1 from "did-session";
import * as i2 from "mr-who/dom";
import * as i3 from "navigo";
import * as i4 from "flowbite";
import * as i5 from "mr-who/utils";
import * as i6 from "@didtools/pkh-ethereum";
import * as i7 from "@composedb/client";
import * as i8 from "flowbite-datepicker";
import * as i9 from "mr-who/render";
import * as i10 from "mr-who/mutations";
import * as i11 from "viem/chains";
import * as i12 from "co-blue/icons";
import * as i13 from "viem";
import * as i14 from "viem/accounts";

const ALL = {};

globalThis.shadow$bridge = function(name) {
  const ret = ALL[name];
  if (ret == undefined) {
    throw new Error("Dependency: " + name + " not provided by external JS!");
  } else {
    return ret;
  }
};

ALL["blockies-ts"] = i0;

ALL["did-session"] = i1;

ALL["mr-who/dom"] = i2;

ALL["navigo"] = i3;

ALL["flowbite"] = i4;

ALL["mr-who/utils"] = i5;

ALL["@didtools/pkh-ethereum"] = i6;

ALL["@composedb/client"] = i7;

ALL["flowbite-datepicker"] = i8;

ALL["mr-who/render"] = i9;

ALL["mr-who/mutations"] = i10;

ALL["viem/chains"] = i11;

ALL["co-blue/icons"] = i12;

ALL["viem"] = i13;

ALL["viem/accounts"] = i14;
