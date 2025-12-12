-- Überprüfe Contracts ohne group_type_id
SELECT 
    'Contracts ohne group_type_id:' as info,
    COUNT(*) as anzahl
FROM contract 
WHERE group_type_id IS NULL

UNION ALL

SELECT 
    'Contracts mit group_type_id:' as info,
    COUNT(*) as anzahl
FROM contract 
WHERE group_type_id IS NOT NULL; 