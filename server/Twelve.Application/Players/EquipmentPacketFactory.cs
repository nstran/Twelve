using System.Collections.Generic;
using System.IO;
using Twelve.Core.Entities;
using Twelve.Core.GameLogic;
using Twelve.Core.Players;
using Twelve.Core.Tlv;

namespace Twelve.Application.Players
{
    /// <summary>
    /// Builds TLV-encoded equipment payloads that match the Java client parser
    /// <c>ky.a(ku, int, int, boolean)</c> in <c>ky.java:1269-1306</c>.
    ///
    /// Two serialization modes:
    /// - <b>Minimal</b> (bl2=false): tags 84/4/139/27 — used for other-player preview, map actors.
    /// - <b>Full</b> (bl2=true): all minimal tags + name/stats/durability/rank/gender/element/etc.
    ///   Used for own inventory, equip detail, shop preview.
    ///
    /// Each equipment record is wrapped in tag 83 (EquipmentArray) as a nested TLV blob.
    /// The Java client reads <c>ku.b((short)83)</c> to get the count, then iterates
    /// <c>ku.b((short)83, offset)</c> to parse each nested record.
    ///
    /// Source note: tag IDs and data types derived from ky.java parser + lb.java stat block.
    /// </summary>
    public sealed class EquipmentPacketFactory
    {
        /// <summary>
        /// Serialize a single equipment entry as a minimal TLV record (bl2=false).
        /// Java evidence: ky.java:1270-1275.
        /// Tags: 84(slot/byte), 4(resourceId/int), 139(durability/int), 27(enhanceLevel/int).
        /// </summary>
        public static byte[] BuildMinimalRecord(PlayerEquipmentEntry entry)
        {
            using var ms = new MemoryStream();
            // tag 84: slot (byte) — ll.e
            ms.Write(TlvCodec.MakeTag((int)TagCode.EquipmentSlot, (byte)entry.Slot));
            // tag 4: resourceId (int) — ll.n
            ms.Write(TlvCodec.MakeTag((int)TagCode.ResourceId, entry.ResourceId));
            // tag 139: current durability (int) — ll.p
            ms.Write(TlvCodec.MakeTag((int)TagCode.CurrentDurability, entry.Durability));
            // tag 27: enhancement level (int) — ll.j
            ms.Write(TlvCodec.MakeTag((int)TagCode.EnhancementLevel, entry.Level));
            return ms.ToArray();
        }

        /// <summary>
        /// Serialize a single equipment entry as a full TLV record (bl2=true).
        /// Java evidence: ky.java:1269-1306.
        /// Includes all minimal tags + detail/stat tags for own-inventory/equip-detail view.
        /// </summary>
        public static byte[] BuildFullRecord(PlayerEquipmentEntry entry, PlayerEquipmentDefinition definition)
        {
            using var ms = new MemoryStream();

            // ── Minimal tags (same as bl2=false) ──
            // tag 84: slot (byte) — ll.e
            ms.Write(TlvCodec.MakeTag((int)TagCode.EquipmentSlot, (byte)entry.Slot));
            // tag 4: resourceId (int) — ll.n
            ms.Write(TlvCodec.MakeTag((int)TagCode.ResourceId, entry.ResourceId));
            // tag 139: current durability (int) — ll.p
            ms.Write(TlvCodec.MakeTag((int)TagCode.CurrentDurability, entry.Durability));
            // tag 27: enhancement level (int) — ll.j
            ms.Write(TlvCodec.MakeTag((int)TagCode.EnhancementLevel, entry.Level));

            // ── Full tags (bl2=true only) ──
            // tag 26: display name (string) — ll.d
            ms.Write(TlvCodec.MakeTag((int)TagCode.DisplayName, definition.DisplayName));
            // tag 135: required level (int) — ll.i
            ms.Write(TlvCodec.MakeTag((int)TagCode.RequiredLevel, definition.RequiredLevel));
            // tag 15: element icon (byte) — ll.f
            ms.Write(TlvCodec.MakeTag((int)TagCode.ElementIcon, (byte)definition.ElementIcon));
            // tag 16: gender (byte) — ll.h
            ms.Write(TlvCodec.MakeTag((int)TagCode.EquipmentGender, (byte)definition.Gender));
            // tag 138: rank (byte) — ll.m
            ms.Write(TlvCodec.MakeTag((int)TagCode.EquipmentRank, (byte)definition.Rank));
            // tag 144: max durability (int) — ll.q
            ms.Write(TlvCodec.MakeTag((int)TagCode.MaxDurability, entry.MaxDurability));
            // tag 117: summary/description (string) — ll.g
            ms.Write(TlvCodec.MakeTag((int)TagCode.Summary, definition.Summary));
            // tag 156: unknown_s (byte) — ll.s; pending/unverified, write -1 as default
            ms.Write(TlvCodec.MakeTag((int)TagCode.UnknownS, (byte)0xFF));
            // tag 85: tradeable (byte) — ll.t; 1=tradeable, 0=bound
            ms.Write(TlvCodec.MakeTag((int)TagCode.Tradeable, (byte)(definition.Tradeable ? 1 : 0)));
            // tag 190: repair cost indicator (byte) — ll.k; Java ll.c() returns k > 0
            ms.Write(TlvCodec.MakeTag((int)TagCode.RepairCost, (byte)(definition.RepairCost > 0 ? 1 : 0)));

            // ── Stat block: lb.java 15 fields ──
            var mod = definition.Modifier;
            // tag 118: Strength (int) — lb.a
            ms.Write(TlvCodec.MakeTag((int)TagCode.CuongLuc, mod.CuongLuc));
            // tag 119: Agility (int) — lb.b
            ms.Write(TlvCodec.MakeTag((int)TagCode.ThanPhap, mod.ThanPhap));
            // tag 120: Magic (int) — lb.c
            ms.Write(TlvCodec.MakeTag((int)TagCode.NoiLuc, mod.NoiLuc));
            // tag 121: Vitality (int) — lb.d
            ms.Write(TlvCodec.MakeTag((int)TagCode.TheLuc, mod.TheLuc));
            // tag 72: Attack (int) — lb.e
            ms.Write(TlvCodec.MakeTag(72, mod.FlatAttack));
            // tag 71: Defense (int) — lb.f
            ms.Write(TlvCodec.MakeTag(71, mod.Defense));
            // tag 126: Crit (int) — lb.g
            ms.Write(TlvCodec.MakeTag(126, mod.Crit));
            // tag 124: Dodge (int) — lb.h
            ms.Write(TlvCodec.MakeTag(124, mod.Dodge));
            // tag 47: HP (int) — lb.i
            ms.Write(TlvCodec.MakeTag((int)TagCode.JavaMaxHp, mod.MaxHp));
            // tag 200: DamageAbsorbPercent (int) — lb.j
            ms.Write(TlvCodec.MakeTag((int)TagCode.DamageAbsorbPercent, mod.DamageAbsorbPercent));
            // tag 201: ArmorPiercePercent (int) — lb.k
            ms.Write(TlvCodec.MakeTag((int)TagCode.ArmorPiercePercent, mod.ArmorPiercePercent));
            // tag 202: BlockPercent (int) — lb.l
            ms.Write(TlvCodec.MakeTag((int)TagCode.BlockPercent, mod.BlockPercent));
            // tag 203: RevivePercent (int) — lb.m
            ms.Write(TlvCodec.MakeTag((int)TagCode.RevivePercent, mod.RevivePercent));
            // tag 204: AttackPercent (int) — lb.n
            ms.Write(TlvCodec.MakeTag((int)TagCode.AttackPercent, mod.AttackPercent));
            // tag 221: HpPercent (int) — lb.o
            ms.Write(TlvCodec.MakeTag((int)TagCode.HpPercent, mod.HpPercent));

            return ms.ToArray();
        }

        /// <summary>
        /// Wrap an array of equipment records into repeated tag-83 containers.
        /// Java evidence: ks.java sends <c>kx2.a((short)83, kw2.Q)</c> where Q is equipment key/array.
        /// Client reads <c>ku.b((short)83)</c> for count, then iterates nested records.
        /// </summary>
        public static byte[] BuildEquipmentArrayPayload(IReadOnlyList<byte[]> records)
        {
            using var ms = new MemoryStream();
            foreach (var record in records)
            {
                ms.Write(TlvCodec.MakeTag((int)TagCode.EquipmentArray, record));
            }
            return ms.ToArray();
        }

        /// <summary>
        /// Build full equipment array payload from aggregate equipment list + catalog resolver.
        /// Convenience method for login/character-info flow.
        /// </summary>
        public static byte[] BuildFullEquipmentPayload(
            IReadOnlyList<PlayerEquipmentEntry> equipment,
            PlayerContentCatalog catalog)
        {
            var records = new List<byte[]>(equipment.Count);
            foreach (var entry in equipment)
            {
                var definition = string.IsNullOrWhiteSpace(entry.TemplateKey)
                    ? null
                    : catalog.GetEquipmentDefinition(entry.TemplateKey);
                if (definition is null)
                {
                    // Fallback to minimal if definition not found (should not happen in normal flow)
                    records.Add(BuildMinimalRecord(entry));
                }
                else
                {
                    records.Add(BuildFullRecord(entry, definition));
                }
            }
            return BuildEquipmentArrayPayload(records);
        }

        /// <summary>
        /// Build minimal equipment array payload (for other-player preview / map actors).
        /// </summary>
        public static byte[] BuildMinimalEquipmentPayload(IReadOnlyList<PlayerEquipmentEntry> equipment)
        {
            var records = new List<byte[]>(equipment.Count);
            foreach (var entry in equipment)
            {
                records.Add(BuildMinimalRecord(entry));
            }
            return BuildEquipmentArrayPayload(records);
        }
    }
}
