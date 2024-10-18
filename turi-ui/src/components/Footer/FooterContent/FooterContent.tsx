import { ReactNode } from 'react'
import { useTranslation } from 'react-i18next'
import FooterModule from '../FooterModule'
import TextExtraLight from '../../Controls/Text/TextExtraLight'
import TextRegular from '../../Controls/Text/TextRegular'
import styles from './FooterContent.module.css'


const FooterContent = ({ content }: { content: ReactNode }) => {
    const { t } = useTranslation();

    return (
        <div className={styles.content}>
            { content }
            <FooterModule
                title={
                    <TextExtraLight
                        text={t('footer.offers')}
                    />
                }
                firstOption={
                    <TextRegular
                        text={t('footer.stays')}
                    />
                }
                secondOption={
                    <TextRegular
                        text={t('footer.attractions')}
                    />
                }
                thirdOption={
                    <TextRegular
                        text={t('footer.rating')}
                    />
                }
            />
            <FooterModule
                title={
                    <TextExtraLight
                        text={t('footer.more')}
                    />
                }
                firstOption={
                    <TextRegular
                        text={t('footer.about')}
                    />
                }
                secondOption={
                    <TextRegular
                        text={t('footer.conditions')}
                    />
                }
                thirdOption={
                    <TextRegular
                        text={t('footer.privacy')}
                    />
                }
                fourthOption={
                    <TextRegular
                        text={t('footer.help')}
                    />
                }
            />
            <FooterModule
                title={
                    <TextExtraLight
                        text={t('footer.contact')}
                    />
                }
                firstOption={
                    <TextRegular
                        text={t('footer.email')}
                    />
                }
                secondOption={
                    <TextRegular
                        text={t('footer.phone-number')}
                    />
                }
            />
        </div>
    )
}

export default FooterContent;