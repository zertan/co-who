import * as i0 from "blockies-ts";
import * as i1 from "did-session";
import * as i2 from "navigo";
import * as i3 from "flowbite";
import * as i4 from "@didtools/pkh-ethereum";
import * as i5 from "@composedb/client";
import * as i6 from "flowbite-datepicker";
import * as i7 from "viem/chains";
import * as i8 from "viem";
import * as i9 from "viem/accounts";

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

ALL["@didtools/pkh-ethereum"] = i4;

ALL["@composedb/client"] = i5;

ALL["flowbite-datepicker"] = i6;

ALL["viem/chains"] = i7;

ALL["viem"] = i8;

ALL["viem/accounts"] = i9;
