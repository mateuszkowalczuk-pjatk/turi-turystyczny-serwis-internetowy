import { useTranslation } from 'react-i18next'
import PersonalPart from '../../../components/Personal/PersonalPart'
import PersonalPanel from '../../../components/Personal/PersonalPanel'
import PersonalLabel from '../../../components/Personal/PersonalLabel'
import PersonalInput from '../../../components/Personal/PersonalInput'
import PersonalGender from '../../../components/Personal/PersonalGender'
import styles from './ProfilePersonalPage.module.css'

const ProfilePersonalPage = () => {
    const { t } = useTranslation();

    return (
        <div className={styles.page}>
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={
                            <PersonalLabel
                                text={t('profile.name-surname')}
                            />
                        }
                        firstInput={
                            <PersonalInput
                                text={t('profile.name')}
                            />
                        }
                        secondInput={
                            <PersonalInput
                                text={t('profile.surname')}
                            />
                        }
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={
                            <PersonalLabel
                                text={t('profile.birthdate')}
                            />
                        }
                        firstInput={
                            <PersonalInput
                                text={t('profile.day')}
                            />
                        }
                        secondInput={
                            <PersonalInput
                                text={t('profile.month')}
                            />
                        }
                        thirdInput={
                            <PersonalInput
                                text={t('profile.year')}
                            />
                        }
                    />
                }
                option={
                    <PersonalPanel
                        label={
                            <PersonalLabel
                                text={t('profile.gender')}
                            />
                        }
                        firstInput={
                            <PersonalGender />
                        }
                    />
                }
            />
            <PersonalPart
                firstPanel={
                    <PersonalPanel
                        label={
                            <PersonalLabel
                                text={t('profile.phone-number')}
                            />
                        }
                        firstInput={
                            <PersonalInput
                                text={t('profile.phone-number')}
                            />
                        }
                    />
                }
                secondPanel={
                    <PersonalPanel
                        label={
                            <PersonalLabel
                                text={t('profile.address')}
                            />
                        }
                        firstInput={
                            <PersonalInput
                                text={t('profile.country')}
                            />
                        }
                        secondInput={
                            <PersonalInput
                                text={t('profile.city')}
                            />
                        }
                        thirdInput={
                            <PersonalInput
                                text={t('profile.zipcode')}
                            />
                        }
                        fourthInput={
                            <PersonalInput
                                text={t('profile.street')}
                            />
                        }
                        fifthInput={
                            <PersonalInput
                                text={t('profile.building')}
                            />
                        }
                        sixthInput={
                            <PersonalInput
                                text={t('profile.apartment')}
                            />
                        }
                    />
                }
            />
        </div>
    )
}

export default ProfilePersonalPage