import { useEffect, useState } from 'react'
import { useTranslation } from 'react-i18next'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import ImagePanel from '../../Shared/Image/ImagePanel'
import { Account } from '../../../types'
import { Image } from '../../../types/image.ts'
import { premiumService } from '../../../services/premiumService.ts'
import { accountService } from '../../../services/accountService.ts'
import { imageService } from '../../../services/imageService.ts'
import { userService } from '../../../services/userService.ts'
import styles from './OfferOwner.module.css'

interface Props {
    premiumId: number
    description: string
}

const OfferOwner = ({ premiumId, description }: Props) => {
    const { t } = useTranslation()
    const [account, setAccount] = useState<Account>()
    const [image, setImage] = useState<Image>()
    const [email, setEmail] = useState<string>()

    useEffect(() => {
        const fetchData = async () => {
            if (premiumId) {
                const premiumResponse = await premiumService.getAccountId(premiumId)
                const accountId: number = await premiumResponse.json()
                const accountResponse = await accountService.get(accountId)
                const accountData: Account = await accountResponse.json()
                setAccount(accountData)
                if (accountData.userId) {
                    const emailResponse = await userService.getEmailById(accountData.userId)
                    const emailData = await emailResponse.text()
                    setEmail(emailData)
                }
                if (accountData.accountId) {
                    const imageResponse = await imageService.getByAccount(accountData.accountId)
                    const imageData = await imageResponse.json()
                    setImage(imageData)
                }
            }
        }
        fetchData().catch((error) => error)
    }, [premiumId])

    return (
        <div className={styles.owner}>
            <div className={styles.title}>
                <TextRegular text={t('offer.owner')} />
            </div>
            <div className={styles.information}>
                <div className={styles.image}>
                    {image && (
                        <ImagePanel
                            path={image.path}
                            onlyDisplay
                        />
                    )}
                </div>
                <div className={styles.text}>
                    {account && (
                        <div className={styles.name}>
                            <TextRegular text={account.firstName} />
                            <TextRegular text={account.lastName} />
                        </div>
                    )}
                    <TextRegular text={description} />
                </div>
                <div className={styles.details}>
                    <ul>
                        <li className={styles.customListItem}>
                            <span className={styles.customStep}></span>
                            <TextRegular text={t('offer.owner-email') + email} />
                        </li>
                        <li className={styles.customListItem}>
                            <span className={styles.customStep}></span>
                            <TextRegular text={t('offer.owner-phone-number') + account?.phoneNumber} />
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default OfferOwner
