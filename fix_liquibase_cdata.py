import glob, re

files = glob.glob('src/main/resources/config/liquibase/changelog/2025*.xml')

for f in files:
    with open(f, 'r', encoding='utf-8') as fh:
        content = fh.read()
    
    # Skip if already has CDATA
    if '<![CDATA[' in content:
        print(f'SKIP (already has CDATA): {f}')
        continue
    
    # Wrap every <sql ...> ... </sql> block with CDATA
    def wrap_cdata(m):
        open_tag = m.group(1)
        sql_body = m.group(2)
        return f'{open_tag}<![CDATA[\n{sql_body}]]></sql>'
    
    new_content = re.sub(
        r'(<sql[^>]*>)(.*?)</sql>',
        wrap_cdata,
        content,
        flags=re.DOTALL
    )
    
    if new_content != content:
        with open(f, 'w', encoding='utf-8') as fh:
            fh.write(new_content)
        print(f'FIXED: {f}')
    else:
        print(f'NO CHANGE: {f}')

print('Done.')
