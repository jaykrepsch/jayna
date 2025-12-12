import { IApplicationUser } from '@/shared/model/application-user.model';
import { IGroupType } from '@/shared/model/group-type.model';

export class Document {
  constructor(
    id,
    title,
    description,
    fileName,
    filePath,
    fileType,
    fileSize,
    uploadDate,
    lastModified,
    version,
    status,
    tags,
    keywords,
    contentHash,
    mimeType,
    isPublic,
    expiryDate,
    documentNumber,
    customerNumber,
    partner,
    contractNumber,
    realEstateNumber,
    financeAccountNumber,
    mobilityNumber,
    label,
    entityState,
    comment,
    applicationUser,
    groupType
  ) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.fileName = fileName;
    this.filePath = filePath;
    this.fileType = fileType;
    this.fileSize = fileSize;
    this.uploadDate = uploadDate;
    this.lastModified = lastModified;
    this.version = version;
    this.status = status;
    this.tags = tags;
    this.keywords = keywords;
    this.contentHash = contentHash;
    this.mimeType = mimeType;
    this.isPublic = isPublic ?? false;
    this.expiryDate = expiryDate;
    this.documentNumber = documentNumber;
    this.customerNumber = customerNumber;
    this.partner = partner;
    this.contractNumber = contractNumber;
    this.realEstateNumber = realEstateNumber;
    this.financeAccountNumber = financeAccountNumber;
    this.mobilityNumber = mobilityNumber;
    this.label = label;
    this.entityState = entityState;
    this.comment = comment;
    this.applicationUser = applicationUser;
    this.groupType = groupType;
  }
}
