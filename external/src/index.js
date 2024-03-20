import * as i0 from "blockies-ts";
import * as i1 from "did-session";
import * as i2 from "navigo";
import * as i3 from "flowbite";
import * as i4 from "mr-who/utils";
import * as i5 from "@didtools/pkh-ethereum";
import * as i6 from "@composedb/client";
import * as i7 from "flowbite-datepicker";
import * as i8 from "mr-who/mutations";
import * as i9 from "viem/chains";
import * as i10 from "co-blue/icons";
import * as i11 from "viem";
import * as i12 from "viem/accounts";

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

ALL["navigo"] = i2;

ALL["flowbite"] = i3;

ALL["mr-who/utils"] = i4;

ALL["@didtools/pkh-ethereum"] = i5;

ALL["@composedb/client"] = i6;

ALL["flowbite-datepicker"] = i7;

ALL["mr-who/mutations"] = i8;

ALL["viem/chains"] = i9;

ALL["co-blue/icons"] = i10;

ALL["viem"] = i11;

ALL["viem/accounts"] = i12;
