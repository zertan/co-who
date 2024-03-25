import * as i0 from "blockies-ts";
import * as i1 from "did-session";
import * as i2 from "flowbite";
import * as i3 from "@didtools/pkh-ethereum";
import * as i4 from "@composedb/client";
import * as i5 from "flowbite-datepicker";
import * as i6 from "viem/chains";
import * as i7 from "viem";
import * as i8 from "viem/accounts";

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

ALL["flowbite"] = i2;

ALL["@didtools/pkh-ethereum"] = i3;

ALL["@composedb/client"] = i4;

ALL["flowbite-datepicker"] = i5;

ALL["viem/chains"] = i6;

ALL["viem"] = i7;

ALL["viem/accounts"] = i8;
